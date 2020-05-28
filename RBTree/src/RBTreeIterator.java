import java.util.Iterator;

class RBTreeIterator<T> implements Iterator<T> {

    Node<T> nodeCursor;

    RBTreeIterator(RBTree<T> tree) {
        // initialize cursor
        nodeCursor = tree.root;

        if(nodeCursor != null){

            while(nodeCursor.left != null){
                nodeCursor = nodeCursor.left;
            }
        }
    }

    // Checks if the next element exists
    public boolean hasNext() {
        if(Utils.getSuccessor(this.nodeCursor) == null){
            return false;
        }

        return true;
    }

    // moves the cursor/iterator to next element
    public T next() {
        nodeCursor = Utils.getSuccessor(nodeCursor);
        return nodeCursor.value;
    }
}
