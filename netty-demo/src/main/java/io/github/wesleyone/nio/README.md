# NIO核心对象
`Buffer`、`Channel`、`Selector`

# Buffer
- 缓存区，初始化数组
- 核心属性
    - mark      标记（暂存position）
    - position  当前读写的数组下标位置
    - limit     限制读写操作的下标位置必须小于limit(写读切换后限制超读)
    - capacity  数组的容量大小
- MappedByteBuffer 内存映射缓存区
    
# Channel
## 常用Channel
- ServerSocketChannel、SocketChannel 用于TCP的数据读写
- DatagramChannel 用于UDP的数据读写
- FileChannel用于文件的数据读写


# Selector
- `selectKey` 和 `key` 方法区别
- 事件：`OP_ACCEPT`、`OP_READ`、`OP_WRITE`、`OP_CONNECT`
- `selectionKey::channel`方法获取注册的Channel