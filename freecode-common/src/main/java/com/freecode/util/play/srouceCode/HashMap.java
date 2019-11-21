/*
package com.freecode.util.play.srouceCode;

import sun.misc.SharedSecrets;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

*/
/**
 * 基于哈希表的Map接口实现。此实现提供所有可选的映射操作，并允许 空值和空键。（HashMap 类大致相当于Hashtable，除了它是不同步的并且允许空值。）这个类不保证地图的顺序; 特别是，它不保证订单会随着时间的推移保持不变。
 * 假设散列函数在桶之间正确地分散元素，该实现为基本操作（get和put）提供了恒定时间性能。对集合视图的迭代需要与HashMap实例的“容量” （桶的数量）加上其大小（键 - 值映射的数量）成比例的时间
 * 。因此，如果迭代性能很重要，则不要将初始容量设置得太高（或负载因子太低）非常重要。
 * <p>
 * HashMap的一个实例有两个影响其性能的参数：初始容量和负载因子。的 容量是在哈希表中桶的数量，和初始容量是简单地在创建哈希表中的时间的能力。该 负载系数是的哈希表是如何充分允许获得之前它的容量自动增加的措施。
 * 当哈希表中的条目数超过加载因子和当前容量的乘积时，哈希表将被重新哈希（即，重建内部数据结构），以便哈希表具有大约两倍的桶数。
 * <p>
 * 作为一般规则，默认加载因子（.75）在时间和空间成本之间提供了良好的折衷。较高的值会减少空间开销，但会增加查找成本（反映在HashMap类的大多数操作中，包括 get和put）。
 * 在设置其初始容量时，应考虑映射中的预期条目数及其负载因子，以便最小化重新散列操作的数量。如果初始容量大于最大条目数除以加载因子，则不会发生重新加载操作。
 * <p>
 * 如果要将多个映射存储在HashMap 实例中，则使用足够大的容量创建映射将允许映射更有效地存储，而不是根据需要执行自动重新散列来扩展表。请注意，使用具有相同键的许多键hashCode()是降低任何哈希表性能的可靠方法。为了改善影响，
 * 当键出现时Comparable，这个类可以使用键之间的比较顺序来帮助打破关系。
 * <p>
 * 请注意，此实现不同步。 如果多个线程同时访问哈希映射，并且至少有一个线程在结构上修改了映射，则必须在外部进行同步。（结构修改是添加或删除一个或多个映射的任何操作;仅更改与实例已包含的键相关联的值不是结构修改。）
 * 这通常通过同步自然封装映射的某个对象来完成。 。如果不存在此类对象，则应使用该Collections.synchronizedMap 方法“包装”地图 。这最好在创建时完成，以防止意外地不同步访问地图：
 * <p>
 * Map m = Collections.synchronizedMap（new HashMap（...））;
 * 所有这个类的“集合视图方法”返回的迭代器都是快速失败的：如果在创建迭代器之后的任何时候对映射进行结构修改，除了通过迭代器自己的 remove方法之外，迭代器将抛出一个 ConcurrentModificationException。
 * 因此，面对并发修改，迭代器快速而干净地失败，而不是在将来某个未确定的时间冒着任意的，非确定性的行为风险。
 * <p>
 * 请注意，迭代器的故障快速行为无法得到保证，因为一般来说，在存在非同步并发修改的情况下不可能做出任何硬性保证。失败快速迭代器会尽最大努力抛出ConcurrentModificationException。
 * 因此，编写依赖于此异常的程序以确保其正确性是错误的：迭代器的快速失败行为应该仅用于检测错误。
 * <p>
 * 此类是 Java Collections Framework的成员 。
 * <p>
 * 以来：
 * 1.2
 *//*

class HashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {
 
    private static final long serialVersionUID = 362498820763181265L;
 
*/
/*
HashMap 底层是基于散列算法实现，散列算法分为散列再探测和拉链式。HashMap 则使用了拉链式的散列算法，并在 JDK 1.8 中引入了红黑树优化过长的链表
    实现注意事项。
    链表结构（这里叫 bin ，箱子）
    Map通常充当一个binned（桶）的哈希表，但是当箱子变得太大时，它们就会被转换成TreeNodes的箱子，每个箱子的结构都类似于java.util.TreeMap。
    大多数方法都尝试使用普通的垃圾箱，但是在适用的情况下（只要检查一个节点的实例）就可以传递到TreeNode方法。
    可以像其他的一样遍历和使用TreeNodes，但是在过度填充的时候支持更快的查找
    然而，由于大多数正常使用的箱子没有过多的填充，所以在表方法的过程中，检查树箱的存在可能会被延迟。
    树箱(bins即所有的元素都是TreeNodes）主要是由hashCode来排序的，但是在特定的情况下，
    如果两个元素是相同的“实现了Comparable接口”，那么使用它们的比较方法排序。
    （我们通过反射来保守地检查泛型类型，以验证这一点——参见方法comparableClassFor）。
    使用树带来的额外复杂，是非常有价值的，因为能提供了最坏只有O(log n)的时间复杂度当键有不同的散列或可排序。
    因此,性能降低优雅地在意外或恶意使用hashCode()返回值的分布很差,以及许多key共享一个hashCode,只要他们是可比较的。
    （如果这两种方法都不适用，同时不采取任何预防措施，我们可能会在时间和空间上浪费大约两倍的时间。
    但是，唯一已知的案例源于糟糕的用户编程实践，这些实践已经非常缓慢，这几乎没有什么区别。）
    因为TreeNodes大小大约是普通节点的两倍，所以只有当容器包含足够的节点来保证使用时才使用它们（见treeifythreshold）。
    当它们变得太小（由于移除或调整大小），它们就会被转换回普通bins。
    在使用良好的用户hashcode的用法中，很少使用树箱。
    理想情况下，在随机的hashcode中，箱子中节点的频率遵循泊松分布（http://en.wikipedia.org/wiki/Poisson_distribution），
    默认大小调整阈值为0.75，但由于调整粒度的大小有很大的差异。
    忽略方差，list的长度 k=(exp(-0.5) * pow(0.5, k) / factorial(k))
    第一个值是:
            0:0.60653066
            1:0.30326533
            2:0.07581633
            3:0.01263606
            4:0.00157952
            5:0.00015795
            6:0.00001316
            7:0.00000094
            8:0.00000006
    more: less than 1 in ten million
    树箱（tree bin很难翻译啊！）的根通常是它的第一个节点。
    然而，有时（目前只在Iterator.remove）中，根可能在其他地方，但是可以通过父链接（方法TreeNode.root()）恢复。
    所有适用的内部方法都接受散列码作为参数（通常由公共方法提供），允许它们在不重新计算用户hashcode的情况下调用彼此。
    大多数内部方法也接受一个“标签”参数，通常是当前表，但在调整或转换时可能是新的或旧的。
    当bin列表被树化、分割或取消时（ treeified, split, or untreeified），我们将它们保持在相同的相对存取/遍历顺序（例如
    字段Node.next）为了更好地保存位置，并稍微简化对调用迭代器的拆分和traversals的处理(splits and traversals that invoke iterator.remove)。
    当在插入中使用比较器时，为了在重新平衡中保持一个总排序（或者在这里需要的接近），我们将类和标识符码作为连接开关。
    由于子类LinkedHashMap的存在，普通(plain)与树模型(tree modes)之间的使用和转换变得复杂起来。
    请参阅下面的hook方法，这些方法在插入、删除和访问时被调用，允许LinkedHashMap内部结构保持独立于这些机制。
            （这还要求将Map实例传递给一些可能创建新节点的实用方法。）
    concurrent-programming-like SSA-based编码风格有助于避免在所有扭曲的指针操作中出现混叠错误。
*//*

 
 
      */
/*
       默认的初始容量——必须是2的幂。
       *//*

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
 
    */
/**
     * 最大容量，如果一个更高的值被隐式指定
     * 任何一个有参数的构造函数。
     * 必须是 2 <= 1<<30 的幂。
     *//*

    static final int MAXIMUM_CAPACITY = 1 << 30;
 
    */
/**
     * 在构造函数中没有指定的负载因素。
     *//*

    static final float DEFAULT_LOAD_FACTOR = 0.75f;
 
    */
/*
    树化负载因子
    使用tree bin计数阈值，代替list。在至少有这么多节点的情况下，将一个元素添加到bin，bin会被转换成树。
    这个值必须大于2，并且应该至少是8，与树清除的假设相吻合，即在收缩时将其转换回普通的箱子。
     *//*

    static final int TREEIFY_THRESHOLD = 8;
 
    */
/**
     在调整大小操作过程中，不将a（分割）箱拆放的bin计数阈值。应该小于treeify阈值，最多6个与收缩检测相吻合。
     *//*

    static final int UNTREEIFY_THRESHOLD = 6;
 
    */
/**
     最小的表容量，可以使bin被树化。（否则，如果一个箱子里有太多的节点，表就会被调整。）
     应该至少4 * TREEIFY_THRESHOLD，以避免调整大小和树化阈值之间的冲突。
     *//*

    static final int MIN_TREEIFY_CAPACITY = 64;
 
    */
/**
     基本的哈希bin节点，用于大多数条目。（请参阅下面的TreeNode子类，以及LinkedHashMap的子类。）
     首先 HashMap 内部的结构，它可以看作是数组（Node[] table）和链表结合组成的复合结构，数组被分为一个个桶（bucket），
     通过哈希值决定了键值对在这个数组的寻址；哈希值相同的键值对，则以链表形式存储，
     如果链表大小超过阈值（TREEIFY_THRESHOLD, 8），图中的链表就会被改造为树形结构。
     *//*

    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;
 
        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
 
        public final K getKey() {
            return key;
        }
 
        public final V getValue() {
            return value;
        }
 
        public final String toString() {
            return key + "=" + value;
        }
 
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
 
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
 
        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
 
    */
/* ---------------- 静态的公用工具 -------------- *//*

 
    */
/**
     Computes key.hashCode() and spreads (XORs) higher bits of hash to lower.
     计算key.hashCode（）和利差（XORs）较高的散列值。
     Because the table uses power-of-two masking, sets of hashes that vary only in bits above the current mask will always collide.
     因为这个表使用了两个功能的屏蔽，所以只有在当前掩模上方的位上变化的哈希表总是会发生碰撞。
     (Among known examples are sets of Float keys holding consecutive whole numbers in small tables.)
     （在已知的例子中，有一组浮动键在小表中连续保持整数。）
     So we apply a transform that spreads the impact of higher bits downward.
     所以我们应用了一个变换，将更高位的影响向下传播。
     There is a tradeoff between speed, utility, and quality of bit-spreading.
     在速度、效用和比特传播的质量之间存在权衡。
     Because many common sets of hashes are already reasonably distributed (so don't benefit from spreading),
     and because we use trees to handle large sets of collisions in bins,
     we just XOR some shifted bits in the cheapest possible way to reduce systematic lossage,
     as well as to incorporate impact of the highest bits that would otherwise never be used in index calculations because of table bounds.
     因为许多常见的散列集已经合理分布(所以不要受益于传播),因为我们用树来处理大型的碰撞在垃圾箱,我们只是XOR一些改变以最便宜的方式来减少系统lossage,以及将最高位的影响,否则永远不会因为指数计算中使用的表。
     *//*

    static final int hash(Object key) {
        int h;
        //因为有些数据计算出的哈希值差异主要在高位，而 HashMap 里的哈希寻址是忽略容量以上的高位的，那么这种处理就可以有效避免类似情况下的哈希碰撞。
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
 
    */
/**
     Returns x's Class if it is of the form "class C implements Comparable<C>", else null.
     返回x的类如果它是“class C implements Comparable<C>"”，则为null。
     *//*

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType) t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }
 
    */
/**
     Returns k.compareTo(x) if x matches kc (k's screened comparable class), else 0.
     如果x匹配kc（k的屏幕可比类），则返回k.compareto（x），否则为0。
     *//*

    @SuppressWarnings({"rawtypes", "unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable) k).compareTo(x));
    }
 
    */
/**
     Returns a power of two size for the given target capacity.
     为给定的目标容量返回一个大于等于他的最小的2的指数值
     如给定1->1，3->4 ,4->4 ,5->8 ,9->16,17->32,33->64
     *//*

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
 
    */
/* ---------------- Fields -------------- *//*

 
    */
/**
     The table, initialized on first use, and resized as necessary.
     表在第一次使用时初始化，并在必要时进行调整。
     When allocated, length is always a power of two.
     当分配时，长度总是2的幂。
     (We also tolerate length zero in some operations to allow bootstrapping mechanics that are currently not needed.)
     （我们还可以容忍某些操作的长度为0，以允许目前不需要的引导机制。）
     *//*

    transient Node<K, V>[] table;
 
    */
/**
     Holds cached entrySet().
     保存缓存entrySet()。
     Note that AbstractMap fields are used for keySet() and values().
     请注意，AbstractMap字段用于keySet（）和values（）。
     *//*

    transient Set<Map.Entry<K, V>> entrySet;
 
    */
/**
     * map中k-v键值对的个数。.
     *//*

    transient int size;
 
    */
/**
     The number of times this HashMap has been structurally modified Structural modifications are those that change the number of mappings in the HashMap or otherwise modify its internal structure (e.g., rehash).
     这个HashMap经过结构修改的结构修改的次数，是那些改变HashMap中的映射数量或修改其内部结构（例如，rehash）的修改。
     This field is used to make iterators on Collection-views of the HashMap fail-fast.
     这个字段用于在HashMap故障快速的集合视图上创建迭代器。
     (See ConcurrentModificationException).
     (见ConcurrentModificationException)。
     *//*

    transient int modCount;
 
    */
/**
     * The next size value at which to resize (capacity * load factor).
     下一个大小的值，可以调整大小（容量负载系数）。
     * @serial
     *//*

  */
/* (The javadoc description is true upon serialization.
      （javadoc描述在序列化时是正确的。
    Additionally, if the table array has not been allocated, this field holds the initial array capacity, or zero signifying DEFAULT_INITIAL_CAPACITY.)
    此外，如果表阵列没有被分配，这个字段保留初始数组容量，或者表示默认值。）*//*

    int threshold;
 
    */
/**
     The load factor for the hash table.
     哈希表的负载因素。
     *
     * @serial
     *//*

    final float loadFactor;
 
    */
/* ---------------- Public operations 公共业务-------------- *//*

 
    */
/**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     * 使用指定的初始化容量和负载因子构建一个空的HashMap
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative or the load factor is nonpositive
    如果初始容量是负的或者负载因素是非正的
     *//*

    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }
 
    */
/**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     * 使用指定的初始化容量和默认0.75负载因子构建一个空的HashMap
     *
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     *//*

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
 
    */
/**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     * 使用指定的初始化容量16和默认0.75负载因子构建一个空的HashMap
     *//*

    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
 
    */
/**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the specified <tt>Map</tt>.
     构造一个新的“HashMap”，它的映射与指定的“Map”映射相同。
     The <tt>HashMap</tt> is created with default load factor (0.75) and an initial capacity sufficient to hold the mappings in the specified <tt>Map</tt>.
     HashMap是用默认的load因子（0.75）和初始容量来创建的，它足以容纳指定的映射表中的映射。
     *
     * @param m the map whose mappings are to be placed in this map
     *          映射的映射将被放置在这个Map
     * @throws NullPointerException if the specified map is null
     *//*

    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }
 
    */
/**
     * Implements Map.putAll and Map constructor
     *
     * @param m     the map
     * @param evict false when initially constructing this map, else
     *              true (relayed to method afterNodeInsertion).
     *              当初始化false，否则true（在节点插入后传送到方法）
     *//*

    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
            if (table == null) { // pre-size
                float ft = ((float) s / loadFactor) + 1.0F;
                int t = ((ft < (float) MAXIMUM_CAPACITY) ?
                        (int) ft : MAXIMUM_CAPACITY);
                if (t > threshold)
                    threshold = tableSizeFor(t);
            } else if (s > threshold)
                resize();
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }
 
    */
/**
     * Returns the number of key-value mappings in this map.
     * 返回此Map中键值映射的数量。
     *
     * @return the number of key-value mappings in this map
     *//*

    public int size() {
        return size;
    }
 
    */
/**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     * @return <tt>true</tt> if this map contains no key-value mappings
     *//*

    public boolean isEmpty() {
        return size == 0;
    }
 
    */
/**
     Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     返回指定键被映射的值，如果该映射不包含钥匙的映射，则返回@code null。
     More formally, if this map contains a mapping from a key {@code k} to a value {@code v} such that {@code (key==null ?
    更正式地说，如果这张地图包含从一个键@code k到一个值@code v的映射，那么@code（key==null？
    k==null : key.equals(k))}, then this method returns {@code v};
     k==null：键。equals（k）），然后这个方法返回@code v;
     otherwise it returns {@code null}.
     否则它会返回@code null。
     (There can be at most one such mapping.)
     （最多可以有这样的映射。）
     A return value of {@code null} does not <i>necessarily</i> indicate that the map contains no mapping for the key;
     @code null的返回值并不一定表明map不包含钥匙的映射;
     it's also possible that the map explicitly maps the key to {@code null}.
     也有可能地图显式地将钥匙映射到@code null。
     The {@link #containsKey containsKey} operation may be used to distinguish these two cases.
     @link包含密钥的操作可以用来区分这两种情况。
     * @see #put(Object, Object)
     *//*

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
 
    */
/**
     * Implements Map.get and related methods
     *实现了地图。获取和相关的方法
     * @param hash hash for key
     * @param key  the key
     * @return the node, or null if none
     *//*

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K, V>) first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
 
    */
/**
     * Returns <tt>true</tt> if this map contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *//*

    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }
 
    */
/**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     * 添加kv，如果存在key不存在，添加后返回null。如果key已经存在，则value将会覆盖，同时返回之前的值。
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     *//*

    public V put(K key, V value) {
        //内部调用putVal
        return putVal(hash(key), key, value, false, true);
    }
 
    */
/**
     * Implements Map.put and related methods
     * 关键方法！！！
     *
     * @param hash         hash for key
     *                      key调用hash方法，
     * @param key          the key
     * @param value        the value to put
     * @param onlyIfAbsent if true, don't change existing value
     *                     如果为true则不改变value值，jdk8新增了putIfAbsent，如果不存在则添加。
     * @param evict        if false, the table is in creation mode.
     *                      如果false，表处于创建模式，put方法给的是true
     * @return previous value, or null if none
     *//*

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            //如果tab是空，则使用resize初始化。resize方法兼顾初始化表格，和大小不足时，进行扩容
            n = (tab = resize()).length;
        //键值对在哈希表中的位置i = (n - 1) & hash决定
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            // 这里的逻辑，如果通过hashkey找到Node的节点p，该节点p已经有值，那么就要在链表的后面添加（p也可能已经是红黑树节点）
            Node<K, V> e;
            K k;
            // 如果key相同，则将key相同，那么这里实际上最后执行的操作是将该key的值换成参数value
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
//key不同，那么有2种情况，一种是当前链表已经树化改造，或者还没有树化改造
// 如果是树化改造后的节点，那么新的节点需要插入到红黑色的指定位置
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            else {
// 如果还是链表，实际上这里有个for循环，循环的对象是p的next节点不为null或者p的后续节点有key相同，如果key相同，那么找到这个节点，用新值替换。否则，往链表的最后一点节点添加数据，因为遍历找链表，使用binCount来记录遍历个数，当遍历的个数达到门限值（默认是8）达到8个那么就要发生树化改造
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
// 找到e节点，然后将value赋值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
// 这个方法在LinkedHashMap中能把访问到的元组放入双向链表末端，表示最近使用，（移除的是头部元素）
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        //如果长度大于容量，则扩容
        if (++size > threshold)
            resize();
// 这个方法是空的，在LinkedHashMap中，使用LRU算法，该方法有内容，实际是为了扩展其他
        afterNodeInsertion(evict);
        return null;
    }
 
    */
/**
     Initializes or doubles table size.
     初始化或增加表的大小（扩容）。
     If null, allocates in accord with initial capacity target held in field threshold.
     如果null，则按照在字段阈值中持有的初始容量目标分配。
     Otherwise, because we are using power-of-two expansion, the elements from each bin must either stay at same index, or move with a power of two offset in the new table.
     否则，因为我们使用的是两种扩展的功能，每个箱子中的元素必须保持在相同的索引上，或者在新表中使用两个偏移量。
     *
     * @return the table 
     *//*

    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
// 获取扩容前table长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
// 这个值是2的次方，数组长度
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    else { // preserve order
                    // JDK8之前是transfer方法，多线程操作可能引起cpu100%
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
 
    */
/**
     * 树化改造
     * bin 的数量大于 TREEIFY_THRESHOLD 时：
     如果容量小于 MIN_TREEIFY_CAPACITY，只会进行简单的扩容。
     如果容量大于 MIN_TREEIFY_CAPACITY ，则会进行树化改造。
     * Replaces all linked nodes in bin at index for given hash unless
     * table is too small, in which case resizes instead.
     * 在给定的散列中，替换bin中的所有链接节点，除非表太小，在这种情况下，可以进行调整。
     *//*

    final void treeifyBin(Node<K, V>[] tab, int hash) {
        int n, index;
        Node<K, V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K, V> hd = null, tl = null;
            do {
                TreeNode<K, V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }
 
    */
/**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for
     * any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     *//*

    public void putAll(Map<? extends K, ? extends V> m) {
        putMapEntries(m, true);
    }
 
    */
/**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     *//*

    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ?
                null : e.value;
    }
 
    */
/**
     * Implements Map.remove and related methods
     *给定key的hash值，key值，value值，
     * @param hash       hash for key
     * @param key        the key
     * @param value      the value to match if matchValue, else ignored
     * @param matchValue if true only remove if value is equal  如果为真，只有value和给定的相等才会被删除
     * @param movable    if false do not move other nodes while removing，如果为false，不能移动其他node，当正在删除的时候
     * @return the node, or null if none
     *//*

    final Node<K, V> removeNode(int hash, Object key, Object value,
                                boolean matchValue, boolean movable) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
                if (p instanceof TreeNode)
                    node = ((TreeNode<K, V>) p).getTreeNode(hash, key);
                else {
                    do {
                        if (e.hash == hash &&
                                ((k = e.key) == key ||
                                        (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }
            if (node != null && (!matchValue || (v = node.value) == value ||
                    (value != null && value.equals(v)))) {
                if (node instanceof TreeNode)
                    ((TreeNode<K, V>) node).removeTreeNode(this, tab, movable);
                else if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                ++modCount;
                --size;
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }
 
    */
/**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     * 删除所有元素
     *//*

    public void clear() {
        Node<K, V>[] tab;
        modCount++;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }
 
    */
/**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     * 是否包含这个vlaue
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     *//*

    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        V v;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value ||
                            (value != null && value.equals(v)))
                        return true;
                }
            }
        }
        return false;
    }
 
    */
/**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *返回key的set集合视图，对Map的修改，直接影响这个set集合。
     * 如果映射被修改，而在集合上的迭代正在进行中（除了迭代器自己的“移除”操作），迭代的结果是未定义的(会报错java.util.ConcurrentModificationException，fast-fail)。
     * set支持元素删除，对set的删除操作，直接影响map集合。它通过迭代器从map中删除对应的映射。
     * 但不支持add和addAll操作
     * @return a set view of the keys contained in this map
     *//*

    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }
 
    */
/**
     * 内部内KeySet，内部方法clear、remove分别直接调用了Hashmap中的clear、removeNode方法
     *//*

    final class KeySet extends AbstractSet<K> {
        public final int size() {
            return size;
        }
 
        public final void clear() {
            HashMap.this.clear();
        }
 
        public final Iterator<K> iterator() {
            return new KeyIterator();
        }
 
        public final boolean contains(Object o) {
            return containsKey(o);
        }
 
        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }
 
        public final Spliterator<K> spliterator() {
            return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
 
        public final void forEach(Consumer<? super K> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.key);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }
 
    */
/**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *返回value的collection视图，修改会直接影响HashMap。
     * 如果映射被修改，而在集合上的迭代正在进行中（除了迭代器自己的“移除”操作），迭代的结果是未定义的。
     * (会报错java.util.ConcurrentModificationException，fast-fail),同keySet.
     * 支持元素删除，直接影响map集合。
     * 不支持add和addAll
     * @return a view of the values contained in this map
     *//*

    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }
 
    final class Values extends AbstractCollection<V> {
        public final int size() {
            return size;
        }
 
        public final void clear() {
            HashMap.this.clear();
        }
 
        public final Iterator<V> iterator() {
            return new ValueIterator();
        }
 
        public final boolean contains(Object o) {
            return containsValue(o);
        }
 
        public final Spliterator<V> spliterator() {
            return new ValueSpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
 
        public final void forEach(Consumer<? super V> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.value);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }
 
    */
/**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *返回k-v视图，修改直接影响HashMap，fast-fail
     * 支持删除，不支持增加
     * @return a set view of the mappings contained in this map
     *//*

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }
 
    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        public final int size() {
            return size;
        }
 
        public final void clear() {
            HashMap.this.clear();
        }
 
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }
 
        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }
 
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }
 
        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
 
        public final void forEach(Consumer<? super Map.Entry<K, V>> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }
 
    // Overrides of JDK8 Map extension methods
 
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }
 
    @Override
    public V putIfAbsent(K key, V value) {
        return putVal(hash(key), key, value, true, true);
    }
 
    @Override
    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value, true, true) != null;
    }
 
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K, V> e;
        V v;
        if ((e = getNode(hash(key), key)) != null &&
                ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
            e.value = newValue;
            afterNodeAccess(e);
            return true;
        }
        return false;
    }
 
    @Override
    public V replace(K key, V value) {
        Node<K, V> e;
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
        return null;
    }
 
    @Override
    public V computeIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        if (mappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
            V oldValue;
            if (old != null && (oldValue = old.value) != null) {
                afterNodeAccess(old);
                return oldValue;
            }
        }
        V v = mappingFunction.apply(key);
        if (v == null) {
            return null;
        } else if (old != null) {
            old.value = v;
            afterNodeAccess(old);
            return v;
        } else if (t != null)
            t.putTreeVal(this, tab, hash, key, v);
        else {
            tab[i] = newNode(hash, key, v, first);
            if (binCount >= TREEIFY_THRESHOLD - 1)
                treeifyBin(tab, hash);
        }
        ++modCount;
        ++size;
        afterNodeInsertion(true);
        return v;
    }
 
    public V computeIfPresent(K key,
                              BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        Node<K, V> e;
        V oldValue;
        int hash = hash(key);
        if ((e = getNode(hash, key)) != null &&
                (oldValue = e.value) != null) {
            V v = remappingFunction.apply(key, oldValue);
            if (v != null) {
                e.value = v;
                afterNodeAccess(e);
                return v;
            } else
                removeNode(hash, key, null, false, true);
        }
        return null;
    }
 
    @Override
    public V compute(K key,
                     BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        V oldValue = (old == null) ? null : old.value;
        V v = remappingFunction.apply(key, oldValue);
        if (old != null) {
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            } else
                removeNode(hash, key, null, false, true);
        } else if (v != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, v);
            else {
                tab[i] = newNode(hash, key, v, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return v;
    }
 
    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (value == null)
            throw new NullPointerException();
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        if (old != null) {
            V v;
            if (old.value != null)
                v = remappingFunction.apply(old.value, value);
            else
                v = value;
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            } else
                removeNode(hash, key, null, false, true);
            return v;
        }
        if (value != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, value);
            else {
                tab[i] = newNode(hash, key, value, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return value;
    }
 
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K, V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }
 
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Node<K, V>[] tab;
        if (function == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }
 
    */
/* ------------------------------------------------------------ *//*

    // Cloning and serialization
 
    */
/**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     *克隆
     * @return a shallow copy of this map
     *//*

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        HashMap<K, V> result;
        try {
            result = (HashMap<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.reinitialize();
        result.putMapEntries(this, false);
        return result;
    }
 
    // These methods are also used when serializing HashSets
//    当序列化hashset时，也会使用这些方法
    final float loadFactor() {
        return loadFactor;
    }
 
    final int capacity() {
        return (table != null) ? table.length :
                (threshold > 0) ? threshold :
                        DEFAULT_INITIAL_CAPACITY;
    }
 
    */
/**
     * Save the state of the <tt>HashMap</tt> instance to a stream (i.e.,
     * serialize it).
     *
     * @serialData The <i>capacity</i> of the HashMap (the length of the bucket array) is emitted (int), followed by the <i>size</i> (an int, the number of key-value mappings), followed by the key (Object) and value (Object) for each key-value mapping.
    HashMap的容量（桶数组的长度）被发出（int），然后是大小（一个int，键值映射的数量），然后是每个键值映射的键（Object）和value（Object）。
    The key-value mappings are emitted in no particular order.
    键值映射不是按特定顺序发出的。
     *//*

    private void writeObject(java.io.ObjectOutputStream s)
            throws IOException {
        int buckets = capacity();
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        s.writeInt(buckets);
        s.writeInt(size);
        internalWriteEntries(s);
    }
 
    */
/**
     * Reconstitute the {@code HashMap} instance from a stream (i.e.,
     * deserialize it).
     * 从一个流中重新构建@code HashMap实例（例如，反序列化)。
     *//*

    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        reinitialize();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        s.readInt();                // Read and ignore number of buckets
        int mappings = s.readInt(); // Read number of mappings (size)
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                    mappings);
        else if (mappings > 0) { // (if zero, use defaults)
            // Size the table using given load factor only if within
            // range of 0.25...4.0
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
            float fc = (float) mappings / lf + 1.0f;
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ?
                    DEFAULT_INITIAL_CAPACITY :
                    (fc >= MAXIMUM_CAPACITY) ?
                            MAXIMUM_CAPACITY :
                            tableSizeFor((int) fc));
            float ft = (float) cap * lf;
            threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ?
                    (int) ft : Integer.MAX_VALUE);
 
            // Check Map.Entry[].class since it's the nearest public type to
            // what we're actually creating.
            SharedSecrets.getJavaOISAccess().checkArray(s, Map.Entry[].class, cap);
            @SuppressWarnings({"rawtypes", "unchecked"})
            Node<K, V>[] tab = (Node<K, V>[]) new Node[cap];
            table = tab;
 
            // Read the keys and values, and put the mappings in the HashMap
            for (int i = 0; i < mappings; i++) {
                @SuppressWarnings("unchecked")
                K key = (K) s.readObject();
                @SuppressWarnings("unchecked")
                V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }
        }
    }
 
    */
/* ------------------------------------------------------------ *//*

    // iterators
 
    abstract class HashIterator {
        Node<K, V> next;        // next entry to return
        Node<K, V> current;     // current entry
        int expectedModCount;  // for fast-fail
        int index;             // current slot
 
        HashIterator() {
            expectedModCount = modCount;
            Node<K, V>[] t = table;
            current = next = null;
            index = 0;
            if (t != null && size > 0) { // advance to first entry
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
        }
 
        public final boolean hasNext() {
            return next != null;
        }
 
        final Node<K, V> nextNode() {
            Node<K, V>[] t;
            Node<K, V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }
 
        public final void remove() {
            Node<K, V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }
 
    final class KeyIterator extends HashIterator
            implements Iterator<K> {
        public final K next() {
            return nextNode().key;
        }
    }
 
    final class ValueIterator extends HashIterator
            implements Iterator<V> {
        public final V next() {
            return nextNode().value;
        }
    }
 
    final class EntryIterator extends HashIterator
            implements Iterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            return nextNode();
        }
    }
 
    */
/* ------------------------------------------------------------ *//*

    // spliterators
 
    static class HashMapSpliterator<K, V> {
        final HashMap<K, V> map;
        Node<K, V> current;          // current node
        int index;                  // current index, modified on advance/split
        int fence;                  // one past last index
        int est;                    // size estimate
        int expectedModCount;       // for comodification checks
 
        HashMapSpliterator(HashMap<K, V> m, int origin,
                           int fence, int est,
                           int expectedModCount) {
            this.map = m;
            this.index = origin;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }
 
        final int getFence() { // initialize fence and size on first use
            int hi;
            if ((hi = fence) < 0) {
                HashMap<K, V> m = map;
                est = m.size;
                expectedModCount = m.modCount;
                Node<K, V>[] tab = m.table;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            return hi;
        }
 
        public final long estimateSize() {
            getFence(); // force init
            return (long) est;
        }
    }
 
    static final class KeySpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<K> {
        KeySpliterator(HashMap<K, V> m, int origin, int fence, int est,
                       int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }
 
        public KeySpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new KeySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }
 
        public void forEachRemaining(Consumer<? super K> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.key);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
 
        public boolean tryAdvance(Consumer<? super K> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        K k = current.key;
                        current = current.next;
                        action.accept(k);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }
 
        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }
 
    static final class ValueSpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<V> {
        ValueSpliterator(HashMap<K, V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }
 
        public ValueSpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new ValueSpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }
 
        public void forEachRemaining(Consumer<? super V> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.value);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
 
        public boolean tryAdvance(Consumer<? super V> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        V v = current.value;
                        current = current.next;
                        action.accept(v);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }
 
        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0);
        }
    }
 
    static final class EntrySpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(HashMap<K, V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }
 
        public EntrySpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new EntrySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }
 
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
 
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        Node<K, V> e = current;
                        current = current.next;
                        action.accept(e);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }
 
        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }
 
    */
/* ------------------------------------------------------------ *//*

    // LinkedHashMap support
 
 
    */
/*
     * The following package-protected methods are designed to be
     * overridden by LinkedHashMap, but not by any other subclass.
     * Nearly all other internal methods are also package-protected
     * but are declared final, so can be used by LinkedHashMap, view
     * classes, and HashSet.
     *//*

 
    // Create a regular (non-tree) node
    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }
 
    // For conversion from TreeNodes to plain nodes
    Node<K, V> replacementNode(Node<K, V> p, Node<K, V> next) {
        return new Node<>(p.hash, p.key, p.value, next);
    }
 
    // Create a tree bin node
    TreeNode<K, V> newTreeNode(int hash, K key, V value, Node<K, V> next) {
        return new TreeNode<>(hash, key, value, next);
    }
 
    // For treeifyBin
    TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }
 
    */
/**
     * Reset to initial default state.  Called by clone and readObject.
     *//*

    void reinitialize() {
        table = null;
        entrySet = null;
        keySet = null;
        values = null;
        modCount = 0;
        threshold = 0;
        size = 0;
    }
 
    // Callbacks to allow LinkedHashMap post-actions
    void afterNodeAccess(Node<K, V> p) {
    }
 
    void afterNodeInsertion(boolean evict) {
    }
 
    void afterNodeRemoval(Node<K, V> p) {
    }
 
    // Called only from writeObject, to ensure compatible ordering.
    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        Node<K, V>[] tab;
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    s.writeObject(e.key);
                    s.writeObject(e.value);
                }
            }
        }
    }
 
    */
/* ------------------------------------------------------------ *//*

    // Tree bins
 
    */
/**
     * Entry for Tree bins.
     条目树箱。
     Extends LinkedHashMap.
     LinkedHashMap延伸。
     Entry (which in turn extends Node) so can be used as extension of either regular or linked node.
     条目（反过来扩展节点），因此可以用作常规或链接节点的扩展。
     *//*

    static final class TreeNode<K, V> extends LinkedHashMap.Entry<K, V> {
        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;    // needed to unlink next upon deletion
        boolean red;
 
        TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }
 
        */
/**
         * Returns root of tree containing this node.
         * 返回包含该节点的树根的根。
         *//*

        final TreeNode<K, V> root() {
            for (TreeNode<K, V> r = this, p; ; ) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }
 
        */
/**
         * Ensures that the given root is the first node of its bin.
         * 确保给定的根是其容器的第一个节点。
         *//*

        static <K, V> void moveRootToFront(Node<K, V>[] tab, TreeNode<K, V> root) {
            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {
                int index = (n - 1) & root.hash;
                TreeNode<K, V> first = (TreeNode<K, V>) tab[index];
                if (root != first) {
                    Node<K, V> rn;
                    tab[index] = root;
                    TreeNode<K, V> rp = root.prev;
                    if ((rn = root.next) != null)
                        ((TreeNode<K, V>) rn).prev = rp;
                    if (rp != null)
                        rp.next = rn;
                    if (first != null)
                        first.prev = root;
                    root.next = first;
                    root.prev = null;
                }
                assert checkInvariants(root);
            }
        }
 
        */
/**
         Finds the node starting at root p with the given hash and key.
         找到从根p开始的节点，并使用给定的散列和键。
         The kc argument caches comparableClassFor(key) upon first use comparing keys.
         kc的参数在第一次使用比较键时缓存了比较ableclassfor（键）。
         *//*

        final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
            TreeNode<K, V> p = this;
            do {
                int ph, dir;
                K pk;
                TreeNode<K, V> pl = p.left, pr = p.right, q;
                if ((ph = p.hash) > h)
                    p = pl;
                else if (ph < h)
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if (pl == null)
                    p = pr;
                else if (pr == null)
                    p = pl;
                else if ((kc != null ||
                        (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0)
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.find(h, k, kc)) != null)
                    return q;
                else
                    p = pl;
            } while (p != null);
            return null;
        }
 
        */
/**
         * Calls find for root node.
         *//*

        final TreeNode<K, V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).find(h, k, null);
        }
 
        */
/**
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         *//*

        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                    (d = a.getClass().getName().
                            compareTo(b.getClass().getName())) == 0)
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                        -1 : 1);
            return d;
        }
 
        */
/**
         * Forms tree of the nodes linked from this node.
         *从这个节点链接的节点的表单树
         * @return root of tree
         *//*

        final void treeify(Node<K, V>[] tab) {
            TreeNode<K, V> root = null;
            for (TreeNode<K, V> x = this, next; x != null; x = next) {
                next = (TreeNode<K, V>) x.next;
                x.left = x.right = null;
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                } else {
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    for (TreeNode<K, V> p = root; ; ) {
                        int dir, ph;
                        K pk = p.key;
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else if ((kc == null &&
                                (kc = comparableClassFor(k)) == null) ||
                                (dir = compareComparables(kc, k, pk)) == 0)
                            dir = tieBreakOrder(k, pk);
 
                        TreeNode<K, V> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            moveRootToFront(tab, root);
        }
 
        */
/**
         * Returns a list of non-TreeNodes replacing those linked from
         * this node.
         * 返回一个non-TreeNodes的列表，以替换那些与该节点相关联的节点。
         *//*

        final Node<K, V> untreeify(HashMap<K, V> map) {
            Node<K, V> hd = null, tl = null;
            for (Node<K, V> q = this; q != null; q = q.next) {
                Node<K, V> p = map.replacementNode(q, null);
                if (tl == null)
                    hd = p;
                else
                    tl.next = p;
                tl = p;
            }
            return hd;
        }
 
        */
/**
         * Tree version of putVal.
         * 树版本的putVal
         *//*

        final TreeNode<K, V> putTreeVal(HashMap<K, V> map, Node<K, V>[] tab,
                                        int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            TreeNode<K, V> root = (parent != null) ? root() : this;
            for (TreeNode<K, V> p = root; ; ) {
                int dir, ph;
                K pk;
                if ((ph = p.hash) > h)
                    dir = -1;
                else if (ph < h)
                    dir = 1;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                        (kc = comparableClassFor(k)) == null) ||
                        (dir = compareComparables(kc, k, pk)) == 0) {
                    if (!searched) {
                        TreeNode<K, V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                                (q = ch.find(h, k, kc)) != null) ||
                                ((ch = p.right) != null &&
                                        (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }
 
                TreeNode<K, V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K, V> xpn = xp.next;
                    TreeNode<K, V> x = map.newTreeNode(h, k, v, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null)
                        ((TreeNode<K, V>) xpn).prev = x;
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }
 
        */
/**
         Removes the given node, that must be present before this call.
         删除给定的节点，该节点必须在调用之前出现。
         This is messier than typical red-black deletion code because we cannot swap the contents of an interior node with a leaf successor that is pinned by "next" pointers that are accessible independently during traversal.
         这比典型的红黑删除代码更混乱，因为我们不能将内部节点的内容与叶子的继承者进行交换，而叶子的继承者是由“下一个”指针所固定的，这些指针在遍历过程中是独立访问的。
         So instead we swap the tree linkages.
         所以我们交换了树的连杆。
         If the current tree appears to have too few nodes, the bin is converted back to a plain bin.
         如果当前树的节点数量太少，那么这个箱子就会被转换回一个普通的箱子。
         (The test triggers somewhere between 2 and 6 nodes, depending on tree structure).
         （测试在2到6个节点之间触发，这取决于树的结构）。
         *//*

        final void removeTreeNode(HashMap<K, V> map, Node<K, V>[] tab,
                                  boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            int index = (n - 1) & hash;
            TreeNode<K, V> first = (TreeNode<K, V>) tab[index], root = first, rl;
            TreeNode<K, V> succ = (TreeNode<K, V>) next, pred = prev;
            if (pred == null)
                tab[index] = first = succ;
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;
            if (first == null)
                return;
            if (root.parent != null)
                root = root.root();
            if (root == null || root.right == null ||
                    (rl = root.left) == null || rl.left == null) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            TreeNode<K, V> p = this, pl = left, pr = right, replacement;
            if (pl != null && pr != null) {
                TreeNode<K, V> s = pr, sl;
                while ((sl = s.left) != null) // find successor
                    s = sl;
                boolean c = s.red;
                s.red = p.red;
                p.red = c; // swap colors
                TreeNode<K, V> sr = s.right;
                TreeNode<K, V> pp = p.parent;
                if (s == pr) { // p was s's direct parent
                    p.parent = s;
                    s.right = p;
                } else {
                    TreeNode<K, V> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        else
                            sp.right = p;
                    }
                    if ((s.right = pr) != null)
                        pr.parent = s;
                }
                p.left = null;
                if ((p.right = sr) != null)
                    sr.parent = p;
                if ((s.left = pl) != null)
                    pl.parent = s;
                if ((s.parent = pp) == null)
                    root = s;
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            } else if (pl != null)
                replacement = pl;
            else if (pr != null)
                replacement = pr;
            else
                replacement = p;
            if (replacement != p) {
                TreeNode<K, V> pp = replacement.parent = p.parent;
                if (pp == null)
                    root = replacement;
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                p.left = p.right = p.parent = null;
            }
 
            TreeNode<K, V> r = p.red ? root : balanceDeletion(root, replacement);
 
            if (replacement == p) {  // detach 分离
                TreeNode<K, V> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }
 
        */
/**
         * Splits nodes in a tree bin into lower and upper tree bins, or untreeifies if now too small.
         把树箱中的节点分成更小的树箱，或者如果现在太小了，就会被取消。
         Called only from resize;
         叫只从调整;
         see above discussion about split bits and indices.
         参见上面关于分割位和索引的讨论。
         *
         * @param map   the map
         * @param tab   the table for recording bin heads
         * @param index the index of the table being split
         * @param bit   the bit of hash to split on
         *//*

        final void split(HashMap<K, V> map, Node<K, V>[] tab, int index, int bit) {
            TreeNode<K, V> b = this;
            // Relink into lo and hi lists, preserving order
//            重新链接到lo和hi列表，保存顺序
            TreeNode<K, V> loHead = null, loTail = null;
            TreeNode<K, V> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;
            for (TreeNode<K, V> e = b, next; e != null; e = next) {
                next = (TreeNode<K, V>) e.next;
                e.next = null;
                if ((e.hash & bit) == 0) {
                    if ((e.prev = loTail) == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                    ++lc;
                } else {
                    if ((e.prev = hiTail) == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                    ++hc;
                }
            }
 
            if (loHead != null) {
                if (lc <= UNTREEIFY_THRESHOLD)
                    tab[index] = loHead.untreeify(map);
                else {
                    tab[index] = loHead;
                    if (hiHead != null) // (else is already treeified)
                        loHead.treeify(tab);
                }
            }
            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD)
                    tab[index + bit] = hiHead.untreeify(map);
                else {
                    tab[index + bit] = hiHead;
                    if (loHead != null)
                        hiHead.treeify(tab);
                }
            }
        }
 
        */
/* ------------------------------------------------------------ *//*

        // Red-black tree methods, all adapted from CLR
        // 红黑树方法，都是由CLR改编的
 
        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> root,
                                                TreeNode<K, V> p) {
            TreeNode<K, V> r, pp, rl;
            if (p != null && (r = p.right) != null) {
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false;
                else if (pp.left == p)
                    pp.left = r;
                else
                    pp.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }
 
        static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> root,
                                                 TreeNode<K, V> p) {
            TreeNode<K, V> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }
 
        static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> root,
                                                      TreeNode<K, V> x) {
            x.red = true;
            for (TreeNode<K, V> xp, xpp, xppl, xppr; ; ) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                } else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }
 
        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> root,
                                                     TreeNode<K, V> x) {
            for (TreeNode<K, V> xp, xpl, xpr; ; ) {
                if (x == null || x == root)
                    return root;
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (x.red) {
                    x.red = false;
                    return root;
                } else if ((xpl = xp.left) == x) {
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                    }
                    if (xpr == null)
                        x = xp;
                    else {
                        TreeNode<K, V> sl = xpr.left, sr = xpr.right;
                        if ((sr == null || !sr.red) &&
                                (sl == null || !sl.red)) {
                            xpr.red = true;
                            x = xp;
                        } else {
                            if (sr == null || !sr.red) {
                                if (sl != null)
                                    sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (xp = x.parent) == null ?
                                        null : xp.right;
                            }
                            if (xpr != null) {
                                xpr.red = (xp == null) ? false : xp.red;
                                if ((sr = xpr.right) != null)
                                    sr.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                } else { // symmetric 对称的
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<K, V> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                                (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        } else {
                            if (sl == null || !sl.red) {
                                if (sr != null)
                                    sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                        null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = (xp == null) ? false : xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }
 
        */
/**
         * Recursive invariant check
         * 递归不变量检查
         *//*

        static <K, V> boolean checkInvariants(TreeNode<K, V> t) {
            TreeNode<K, V> tp = t.parent, tl = t.left, tr = t.right,
                    tb = t.prev, tn = (TreeNode<K, V>) t.next;
            if (tb != null && tb.next != t)
                return false;
            if (tn != null && tn.prev != t)
                return false;
            if (tp != null && t != tp.left && t != tp.right)
                return false;
            if (tl != null && (tl.parent != t || tl.hash > t.hash))
                return false;
            if (tr != null && (tr.parent != t || tr.hash < t.hash))
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red)
                return false;
            if (tl != null && !checkInvariants(tl))
                return false;
            if (tr != null && !checkInvariants(tr))
                return false;
            return true;
        }
    }
 
}*/
