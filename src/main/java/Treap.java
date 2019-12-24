import javafx.util.Pair;
import java.util.*;

public class Treap<T extends Comparable<T>> implements SortedSet<T> {

    private T value;
    private Integer prior = null;
    private Treap<T> leftTreap = null;
    private Treap<T> rightTreap = null;
    private int size = 0;

    // Для создания дерева с рандомным приоритетом на отрезке [-100; 100]
    private Integer rand() {
        return(int) (Math.random()*(200+1)) - 100;
    }

    Treap(){
        this.value = null;
    }

    private Treap(T value, Integer prior, Treap<T> leftTreap, Treap<T> rightTreap){
        this.value = value;
        this.prior = prior;
        this.leftTreap = leftTreap;
        this.rightTreap = rightTreap;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.value = null;
        this.prior = null;
        this.leftTreap = null;
        this.rightTreap= null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        if (o == null)
            return false;
        Treap<T> find = find(t);
        return find != null;
    }

    private int height (Treap<T> start) {
        if (start == null || start.value == null)
            return 0;
        return Math.max(height(start.leftTreap), height(start.rightTreap)) + 1;
    }

    private Treap<T> find(T value) {
        if (this.value == null)
            return null;
        return find(this, value);
    }

    private Treap<T> find(Treap<T> start, T value) {
        int comp = value.compareTo(start.value);
        if (comp == 0)
            return start;
        else if (comp < 0) {
            if (start.leftTreap == null || start.leftTreap.value == null) return null;
            return find(start.leftTreap, value);
        }
        else {
            if (start.rightTreap == null || start.rightTreap.value == null) return null;
            return find(start.rightTreap, value);
        }
    }

    private Treap<T> findParent(T value) {
        if (!contains(value))
            throw new IllegalArgumentException("Такого значения нет в дереве");
        if (this.value == null) return null;
        return findParent(this, value);
    }

    private Treap<T> findParent(Treap<T> start, T value) {
        int comp = value.compareTo(start.value);
        if(comp == 0)
            return null;    //только для корня
        else if (comp < 0) {
            if (start.leftTreap == null) return start;
            if (start.leftTreap.value.compareTo(value) == 0)
                return start;
            return findParent(start.leftTreap, value);
        }
        else {
            if (start.rightTreap == null) return start;
            if (start.rightTreap.value.compareTo(value) == 0)
                return start;
            return findParent(start.rightTreap, value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new TreapIterator();
    }

    public class TreapIterator implements Iterator<T> {

        private Stack<Treap<T>> stack = new Stack<>();
        private Treap<T> current = null;

        private TreapIterator() {
            Treap<T> treap = Treap.this;
            while (treap != null && treap.value != null) {
                stack.push(treap);
                treap = treap.leftTreap;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            current = nextTreap();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        private Treap<T> nextTreap() {
            Treap<T> treap = stack.pop();
            current = treap;
            if (treap.rightTreap != null && treap.rightTreap.value != null) {
                treap = treap.rightTreap;
                while (treap != null && treap.value != null) {
                    stack.push(treap);
                    treap = treap.leftTreap;
                }
            }
            return current;
        }

        @Override
        public void remove() {
            Treap.this.remove(current.value);
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<T> iterator = iterator();
        for (int i = 0; i < size(); i++)
            if (iterator.hasNext()) {
                array[i] = iterator.next();
            }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Object[] array = toArray();
        if (a.length < size)
            return (T1[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /** Функция "склеивания" двух деревьев в одно **/
    private Treap<T> merge(Treap<T> l, Treap<T> r, Treap<T> res) {
        if (l == null || l.value == null) {
            if (r == null)
                r = new Treap<>();
            res.value = r.value;
            res.prior = r.prior;
            res.leftTreap = r.leftTreap;
            res.rightTreap = r.rightTreap;
            return res;
        }
        if (r == null || r.value == null) {
            res.value = l.value;
            res.prior = l.prior;
            res.leftTreap = l.leftTreap;
            res.rightTreap = l.rightTreap;
            return res;
        }

        if (l.prior < r.prior) {
            Treap<T> newR = new Treap<>();
            merge(l.rightTreap, r, newR);
            Treap<T> newTreap = new Treap<>(l.value, l.prior, l.leftTreap, newR);
            res.value = newTreap.value;
            res.prior = newTreap.prior;
            res.leftTreap = newTreap.leftTreap;
            res.rightTreap = newTreap.rightTreap;
            return res;
        }
        else {
            Treap<T> newL = new Treap<>();
            merge(l, r.leftTreap, newL);
            Treap<T> newTreap = new Treap<>(r.value, r.prior, newL, r.rightTreap);
            res.value = newTreap.value;
            res.prior = newTreap.prior;
            res.leftTreap = newTreap.leftTreap;
            res.rightTreap = newTreap.rightTreap;
            return res;
        }
    }

    /** Функция "разрезания" дерева на левое и правое поддеревья **/
    private Pair<Treap<T>, Treap<T>> split(T value, Treap<T> l, Treap<T> r){
        Treap<T> newTreap = new Treap<>();
        int comp = this.value.compareTo(value);
        if (comp < 0 || comp == 0) {
            if (this.rightTreap == null || this.rightTreap.value == null)
                r = null;
            else {
                Pair<Treap<T>, Treap<T>> pair =  this.rightTreap.split(value, newTreap, r);
                r = pair.getValue();
                newTreap = pair.getKey();
            }
            l = new Treap<>(this.value, this.prior, this.leftTreap, newTreap);
        }
        else  {
            if (this.leftTreap == null || this.leftTreap.value == null)
                l = null;
            else {
                Pair<Treap<T>, Treap<T>> pair = this.leftTreap.split(value, l, newTreap);
                l = pair.getKey();
                newTreap = pair.getValue();
            }
            r = new Treap<>(this.value, this.prior, newTreap, this.rightTreap);
        }
        return new Pair<>(l, r);
    }

    /** Добавление с заданным приоритетом**/
    boolean add(T value, Integer prior) {
        if (value == null || contains(value))
            return false;
        if (isEmpty()) {
            this.value = value;
            this.prior = prior;
            this.leftTreap = new Treap<>();
            this.rightTreap = new Treap<>();
        }
        else {
            Treap<T> left = new Treap<>();
            Treap<T> right = new Treap<>();
            Pair<Treap<T>, Treap<T>> pair = split(value, left, right);
            left = pair.getKey();
            right = pair.getValue();
            Treap<T> middle = new Treap<>(value, prior, new Treap<>(), new Treap<>());
            Treap<T> newL = new Treap<>();
            merge(left, middle, newL);
            Treap<T> res = new Treap<>();
            Treap<T> newTreap = merge(newL, right, res);
            this.value = newTreap.value;
            this.prior = newTreap.prior;
            this.leftTreap = newTreap.leftTreap;
            this.rightTreap = newTreap.rightTreap;
        }
        size++;
        return true;
    }

    /** Добавление с рандомным приоритетом**/
    @Override
    public boolean add(T value) {
        return add(value, rand());
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object t : c)
            if (!contains(t))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean bool = false;
        for (T t : c)
            if (this.add(t))
                bool = true;
        return bool;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean bool = false;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
            if (!c.contains(iterator.next())) {
                iterator.remove();
                bool = true;
            }
        return bool;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        if (this.value == null || !this.contains(value))
            return false;
        Treap<T> treap = find(value);
        Treap<T> res = merge(treap.leftTreap, treap.rightTreap, new Treap<>());
        Treap<T> parent = findParent(value);
        if (parent == null || parent.value == null) {
            this.value = res.value;
            this.prior = res.prior;
            this.leftTreap = res.leftTreap;
            this.rightTreap = res.rightTreap;
        }
        else {
            if (parent.leftTreap != null && parent.leftTreap.value != null && parent.leftTreap.value.compareTo(value) == 0) {
                parent.leftTreap.value = res.value;
                parent.leftTreap.prior = res.prior;
                parent.leftTreap.leftTreap = res.leftTreap;
                parent.leftTreap.rightTreap = res.rightTreap;
            }
            else {
                parent.rightTreap.value = res.value;
                parent.rightTreap.prior = res.prior;
                parent.rightTreap.leftTreap = res.leftTreap;
                parent.rightTreap.rightTreap = res.rightTreap;
            }
        }
        size--;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean bool = false;
        for (Object t : c)
            if (this.contains(t)) {
                this.remove(t);
                bool = true;
            }
        return bool;
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        if (toElement == null && fromElement == null)
            throw new IllegalArgumentException();
        if (toElement == null)
            throw new IllegalArgumentException();
        if (toElement.compareTo(fromElement) == 0)
            throw new IllegalArgumentException();
        return new TreapSubSet(this, fromElement, toElement);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        if (toElement == null)
            throw new IllegalArgumentException();
        return new TreapSubSet(this, null, toElement);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement == null)
            throw new IllegalArgumentException();
        return new TreapSubSet(this, fromElement, null);
    }

    class TreapSubSet extends Treap<T> {
        final T upLimit;
        final T botLimit;
        Treap<T> treap;

        private TreapSubSet (Treap<T> treap, T botLimit, T upLimit) {
            if (botLimit != null && upLimit != null && botLimit.compareTo(upLimit) > 0)
                throw new IllegalArgumentException("Границы заданы некорректно");
            this.botLimit = botLimit;
            this.upLimit = upLimit;
            this.treap = treap;
        }

        private boolean onInterval(T value) {
            return (botLimit == null && value.compareTo(upLimit) < 0) ||
                    (upLimit == null && value.compareTo(botLimit) >= 0) ||
                    (botLimit != null && upLimit != null && value.compareTo(botLimit) >= 0 && value.compareTo(upLimit) < 0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(Object o) {
            return onInterval((T) o) && treap.contains(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object t : c)
                if (!contains(t))
                    return false;
            return true;
        }

        @Override
        public boolean add(T t) {
            if (!onInterval(t))
                throw new IllegalArgumentException();
            return  treap.add(t);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            boolean b = false;
            for (T t : c)
                if (this.add(t)) b = true;
            return b;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean remove(Object o) {
            if (!onInterval((T) o))
                throw new IllegalArgumentException();
            return treap.remove(o);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean bool = false;
            for (Object t : c)
                if (this.contains(t)) {
                    this.remove(t);
                    bool = true;
                }
            return bool;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean b = false;
            Iterator<T> iterator = iterator();
            while (iterator.hasNext())
                if (!c.contains(iterator.next())) {
                    iterator.remove();
                    b = true;
                }
            return b;
        }

        @Override
        public int size() {
            return size(treap);
        }

        private int size(Treap<T> treap) {
            int count = 0;
            if (treap == null)
                return count;
            count += size(treap.leftTreap);
            if (onInterval(treap.value))
                count++;
            count += size(treap.rightTreap);
            return count;
        }

        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            if (toElement == null && fromElement == null)
                throw new IllegalArgumentException();
            if (toElement == null)
                throw new IllegalArgumentException();
            if (toElement.compareTo(fromElement) == 0)
                throw new IllegalArgumentException();
            return new TreapSubSet(this, fromElement, toElement);
        }

        @Override
        public SortedSet<T> headSet(T toElement) {
            if (toElement == null)
                throw new IllegalArgumentException();
            return new TreapSubSet(this, null, toElement);
        }

        @Override
        public SortedSet<T> tailSet(T fromElement) {
            if (fromElement == null)
                throw new IllegalArgumentException();
            return new TreapSubSet(this, fromElement, null);
        }

        @Override
        public Iterator<T> iterator() {
            return new TreapSubSetIterator();
        }

        private class TreapSubSetIterator extends TreapIterator {

            Iterator<T> iterator = Treap.this.iterator();
            T next = null;

            TreapSubSetIterator() {
                while (iterator.hasNext()) {
                    T el = iterator.next();
                    if (onInterval(el)) {
                        next = el;
                        break;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                if (next == null)
                    throw new NoSuchElementException();
                T el = next;
                if (iterator.hasNext()) {
                    next = iterator.next();
                } else {
                    next = null;
                }
                if (!onInterval(next)) next = null;
                return el;
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        }

        @Override
        public T first() {
            for (T el : treap)
                if (onInterval(el))
                    return el;
                throw new NoSuchElementException();
        }

        @Override
        public T last() {
            T res = null;
            for (T el : treap)
                if (onInterval(el))
                    res = el;
         if (res == null)
             throw new NoSuchElementException();
         return res;
        }
    }

    @Override
    public T first() {
        if (this.value == null) throw new NoSuchElementException();
        Treap<T> current = this;
        while (current.leftTreap != null && current.leftTreap.value != null)
            current = current.leftTreap;
        return current.value;
    }

    @Override
    public T last() {
        if (this.value == null) throw new NoSuchElementException();
        Treap<T> current = this;
        while (current.rightTreap != null && current.rightTreap.value != null)
            current = current.rightTreap;
        return current.value;
    }
}