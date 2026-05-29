##### ROCKET MQ使用注意
1. 生产者和消费者的「主题（Topic）必须一致」，否则消费者无法接收消息；
2. 消费者类必须添加 @Component 注解，交给 Spring 管理，否则无法订阅主题；
3. @RocketMQMessageListener 注解的 consumerGroup 必须与配置文件中 rocketmq.consumer.group 一致；
4. 启动项目前，必须先启动 RocketMQ（NameServer + Broker），否则项目会报错“无法连接 NameServer”；
5. 消息类型要一致：生产者发送 String 类型，消费者也要用 String 类型接收（后续可扩展对象消息）。

##### 使用rocketMQTemplate发送消息的一些方法
1. syncSend(String destination, Object payload) 同步消息，消息发送后，线程会同步等待，直到 Broker 端返回明确的发送结果。对消息送达可靠性要求极高，且业务逻辑需要根据发送结果（成功/失败）做出相应处理的场景。
2. asyncSend(String destination, Object payload, SendCallback sendCallback) 异步消息，方法调用后立即返回，不阻塞主线程。消息发送成功或失败的结果，会通过回调函数 SendCallback 来通知。对响应时间敏感，吞吐量要求较高的场景，例如日志处理。
3. sendOneWay(String destination, Object payload) 单向消息，只负责发送，完全不关心发送结果，既不等待响应，也没有回调。可靠性要求极低，但要求极致性能的场景，比如日志收集、监控数据上报。
4. syncSendOrderly(String destination, Object payload, String hashKey)，顺序消息，通过一个业务标识（hashKey）将消息路由到固定的队列，确保同一 hashKey 的消息被同一个消费者实例按发送顺序严格消费。需要严格保证消息消费顺序的业务，如订单状态流转。消费者端也需配置 consumeMode = ConsumeMode.ORDERLY。
5. syncSend(String destination, Message<?> message, long timeout, int delayLevel),延迟消息，消息不会立即投递，而是在设定的延迟时间（或延迟级别）过后才变得对消费者可见。定时任务、订单超时未支付取消、重试机制等。delayLevel 对应预定义的延迟级别（如1s 5s 10s 30s...），或使用 setDeliverTimeMs 设定具体时间戳。
6. syncSend(String destination, Collection<Message> messages)，批量消息，一次发送多条消息，显著减少网络开销，提升吞吐量。需要批量发送大量相似数据，对实时性要求不高的场景，如离线数据同步。批量消息的总大小有限制（通常不超过1MB），且不支持复杂特性，如延迟、事务等
7. sendMessageInTransaction(String destination, Message<?> message, Object arg)，事务消息，用于保证分布式事务最终一致性。关键流程：1. 发送半消息；2. 执行本地事务；3. 若本地事务成功，提交半消息，消费者才能消费。跨系统或跨库的数据一致性场景，例如订单支付成功后扣减库存、增加积分。
8. 消息过滤与队列选择：除了上述发送方式，rocketMQTemplate 还支持对消息做精细控制：消息过滤：通过定义 Tag 或 SQL92 表达式，消费者可以精确地只拉取自己感兴趣的消息。发送时，Tag 通过在 destination（Topic）后拼接 :yourTag 来实现。自定义队列选择器：通过实现 MessageQueueSelector 接口，开发者可以完全控制消息究竟发送到 Topic 下的哪一个具体队列中，实现更精细的路由策略。

##### 自动创建主题
1. 启动 Broker 时添加参数 autoCreateTopicEnable=true（本地安装）

##### 订阅关系一致
1. 一个消费组内的所有监听器必须严格遵循“订阅关系一致”的原则。
2. 订阅关系必须一致：RocketMQ 官方要求，同一个 consumerGroup 下的所有消费者实例，订阅的 Topic 和 Tag 必须完全一致。否则会导致消息混乱、消费失败等严重后果。如果有不同的消费逻辑或订阅需求，必须使用不同的 consumerGroup。
3. 

#### convertAndSend
1. 通过分析源码可知，convertAndSend 方法内部最终还是调用了 syncSend 来完成实际的消息发送。因此，两者都会阻塞当前线程，直到 Broker 返回发送结果，是一种可靠的同步发送
2. convertAndSend 和 syncSend 底层的通信行为其实是一样的，都是可靠的同步发送，都会同步阻塞等待 Broker 的响应。syncSend 虽然也提供了类似功能，但因其参数处理方式的差异，运行速度反而会慢一些，因此在官方推荐的便捷性场景下，convertAndSend 更合适
3. 选择 convertAndSend：在大多数常规业务场景中，为了代码的简洁性，应优先使用 convertAndSend。它能在无需关心低级消息构建细节的情况下，提供同步发送的可靠性。、
4. 选择 syncSend 及其重载：仅在需要 SendResult 返回值（用于发送状态判断或消息 ID 记录）、精细控制发送超时、设置延迟消息或发送事务消息等高级功能时，才考虑使用 syncSend 或 asyncSend

#### topic和consumer-group的选择
##### 有4类消息，每类消息需要不同的业务逻辑处理。是否必须用4个topic和4个consumer-group？
1. 需要考虑RocketMQ的订阅关系一致性原则：同一个consumer group下的所有消费者必须订阅相同的topic和tag（且表达式一致），否则会报错或导致消息混乱。
2. 如果所有消息都放在同一个topic，通过tag区分消息类型（如tagA, tagB, tagC, tagD），那么可以只用一个topic。
3. 所以建议：使用1个topic + 4个不同的consumer group，每个consumer group订阅相同的topic但不同的tag（通过selectorExpression指定）。这样每个group内的消费者可以只消费特定tag的消息，且不同group之间消费进度独立，互不影响。
4. 或者也可以使用4个topic，每个topic对应一类消息，每个topic配一个consumer group，这样更清晰，但会增加topic数量。从设计上看，如果消息属性差异不大，使用tag区分更轻量；如果消息结构完全不同，或未来可能独立扩展，使用不同topic也是合理的。
5. 答案：不一定需要4个topic，但推荐使用4个consumer group。可以只用1个topic+4个group（通过tag区分），或者4个topic+4个group。
6. 