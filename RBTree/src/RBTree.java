import java.util.Iterator;

public class RBTree <T> implements Iterable<T>{

    // fields
    Node<T> root = null;

    public Iterator<T> iterator(){
        return new RBTreeIterator<>(this);
    }



    // search
    public T search(String key){
        if(key == null){
            return null;
        }


        Node<T> foundNode = internalSearch(key);

        if(foundNode != null){
            return foundNode.value;
        }
        else{
            return null;
        }
    }

    // If a node with key already exists in the tree the value is updated and
    // the last value is returned, otherwise nothing is done and null is returned
    public T update(String key, T value){
        if(key == null){
            return null;
        }

        Node<T> newNode = new Node<>(key, value);

        return internalInsert(newNode, true);
    }

    // inserts a new node into the tree, if a node with the key already exists
    // nothing is done, use update instead if you need an existing value to be updated
    public void insert(String key, T value){
        if(key == null){
            return;
        }

        Node<T> newNode = new Node<>(key, value);
        internalInsert(newNode, false);

        insertFixTree(newNode);

        // find the new root of the tree.
        Node<T> root = this.root;
        while(root.parent != null){
            root = root.parent;
        }

        this.root = root;
        this.root.colour = Colour.BLACK;
        // fix the tree
    }

    Node<T> internalSearch(String key){
        if(this.root == null){
            return null;
        }

        Node<T> currentNode = this.root;

        while(true){

            if(currentNode == null){
                // found a null leaf, node isn't in the tree return null
                // node isn't in the tree,
                return null;
            }

            if(key.compareTo(currentNode.key) == 0){
                // node has the wanted key, return this node
                return currentNode;
            }
            else if(key.compareTo(currentNode.key) < 0){
                // key is less than the current node, traverse the left side of currentNode
                currentNode = currentNode.left;
            }
            else{
                // key is greater than the current node, traverse the right side of currentNode
                currentNode = currentNode.right;
            }
        }
    }



    /*

     */
    void insertFixTree(Node<T> newNode){
        Node<T> uncle = Utils.getUncle(newNode);

        // this is the root node nothing to do
        if(newNode.parent == null){
            return;
        }
        // if the parent is black there is nothing to do
        else if(Utils.getParent(newNode).colour == Colour.BLACK){
            return;
        }

        //case 3
        if(Utils.getNodesColour(uncle) == Colour.RED){
            assert uncle != null;
            uncle.colour = Colour.BLACK;
            Utils.getParent(newNode).colour = Colour.BLACK;
            Utils.getGrandParent(newNode).colour = Colour.RED;
            insertFixTree(Utils.getGrandParent(newNode));
        }
        //case 4
        else{
            Node<T> parent = Utils.getParent(newNode);
            Node<T> grandParent = Utils.getGrandParent(newNode);

            if(newNode == parent.right && parent == grandParent.left){
                Utils.rotateLeft(parent, this);
                // node and parent have swapped places in the tree, so swap the variables
                Node<T> tmpNode = newNode;
                newNode = parent;
                parent = tmpNode;
            }
            else if(newNode == parent.left && parent == grandParent.right){
                Utils.rotateRight(parent, this);
                // node and parent have swapped places in the tree, so swap the variables
                Node<T> tmpNode = newNode;
                newNode = parent;
                parent = tmpNode;
            }

            if(newNode == parent.left){
                Utils.rotateRight(grandParent, this);
            }
            else {
                Utils.rotateLeft(grandParent, this);
            }
            parent.colour = Colour.BLACK;
            grandParent.colour = Colour.RED;
        }

    }

    T internalInsert(Node<T> newNode, boolean update){

        if(this.root == null){
            // the tree is empty, the new node becomes the root
            this.root = newNode;
            newNode.colour = Colour.BLACK;
        }
        else{

            Node<T> cursor = root;

            // traverse the tree and insert the new node
            while(true){
                // the new nodes key is less than than the current nodes key
                if(newNode.key.compareTo(cursor.key) < 0){
                    if(cursor.left == null){
                        // we have found the nodes location, insert it and set its
                        // parent
                        cursor.left = newNode;
                        newNode.parent = cursor;
                        break;
                    }
                    else{
                        // travers the left node and start again
                        cursor = cursor.left;
                    }
                }
                // the new nodes key is greater than the current nodes key
                else if(newNode.key.compareTo(cursor.key) > 0){
                    if(cursor.right == null){
                        // we have found the nodes location, insert it and set its
                        // parent
                        cursor.right = newNode;
                        newNode.parent = cursor;
                        break;
                    }
                    else {
                        cursor = cursor.right;
                    }
                // found an existing node with the same key,
                // update the value
                }else if(update){
                    T oldValue = cursor.value;
                    cursor.value = newNode.value;

                    return oldValue;
                }
            }
        }

        return null;
    }


    // delete
    public void delete(String key){
        if(key == null){
            return;
        }

        Node<T> foundNode = internalSearch(key);

        if(foundNode == null){
            // there is no node to delete just return
            return;
        }
        // if this node is the root and has no children delete it
        else if(Utils.getParent(foundNode) == null && foundNode.left == null && foundNode.right == null){
            this.root = null;
        }

        Node<T> toBeDeleted = getNodeToBeDeleted(foundNode);

        // from this point and beyond toBeDeleted has at most
        // one non null child

        if(toBeDeleted.colour == Colour.RED){
            // case 1
            // nodes colour is red
            deleteNode(toBeDeleted);
        }
        else if (Utils.getSingleChildColour(toBeDeleted) == Colour.RED) {
            // case 2
            // Nodes colour is black and its child is red
            deleteNode(toBeDeleted);
        }
        else{
            // case 3
            // Nodes colour is black and its child is black
            fixUpDeletionCase1(deleteNode(toBeDeleted));
        }
    }

    Node<T> getNodeToBeDeleted(Node<T> foundNode){

        // node has no children
        if(foundNode.left == null && foundNode.right == null){
            return foundNode;
        }
        // node only has a left child
        else if(foundNode.left != null && foundNode.right == null) {
            foundNode.key = foundNode.left.key;
            return foundNode.left;
        }
        // node only has a right child
        else if(foundNode.left == null){
            foundNode.key = foundNode.right.key;
            return foundNode.right;
        }
        // node has both children
        else{
            Node<T> toBeDeleted = Utils.getPredecessor(foundNode);
            assert toBeDeleted != null;
            foundNode.key = toBeDeleted.key;
            foundNode.value = toBeDeleted.value;

            return toBeDeleted;
        }
    }

    void fixUpDeletionCase1(Node<T> doubleBlack){
        if(doubleBlack.parent == null){
            doubleBlack.colour = Colour.BLACK;
            return;
        }

        fixUpDeletionCase2(doubleBlack);
    }

    void fixUpDeletionCase2(Node<T> doubleBlack){
        Node<T> sibling = Utils.getSibling(doubleBlack);

        if(doubleBlack.parent.colour == Colour.BLACK && Utils.getNodesColour(sibling) == Colour.RED){
            if(doubleBlack.parent.left == doubleBlack){
                Utils.rotateLeft(doubleBlack.parent, this);
            }
            else{
                Utils.rotateRight(doubleBlack.parent, this);
            }

            doubleBlack.parent.colour = Colour.RED;
            Utils.getGrandParent(doubleBlack).colour = Colour.BLACK;
        }

        fixUpDeletionCase3(doubleBlack);
    }

    void fixUpDeletionCase3(Node<T> doubleBlack){
        Node<T> sibling = Utils.getSibling(doubleBlack);

        if(doubleBlack.parent.colour == Colour.BLACK && Utils.getNodesColour(sibling) == Colour.BLACK
        && Utils.getNodesColour(sibling.left) == Colour.BLACK && Utils.getNodesColour(sibling.right) == Colour.BLACK){
            sibling.colour = Colour.RED;

            if(doubleBlack.key == null){
                // delete this node as its really a null

                Node<T> toDelete = doubleBlack;
                doubleBlack = doubleBlack.parent;

                if(toDelete.parent.left == toDelete){
                    toDelete.parent.left = null;
                }
                else{
                    toDelete.parent.right = null;
                }

                toDelete.parent = null;
            }
            else{
                doubleBlack.colour = Colour.BLACK;
                doubleBlack = doubleBlack.parent;
            }

            fixUpDeletionCase1(doubleBlack);
        }
        else{
            fixUpDeletionCase4(doubleBlack);
        }
    }

    void fixUpDeletionCase4(Node<T> doubleBlack){
        Node<T> sibling = Utils.getSibling(doubleBlack);

        if(doubleBlack.parent.colour == Colour.RED && Utils.getNodesColour(sibling) == Colour.BLACK
            && Utils.getNodesColour(sibling.left) == Colour.BLACK && Utils.getNodesColour(sibling.right) == Colour.BLACK){
            sibling.colour = Colour.RED;
            doubleBlack.parent.colour = Colour.BLACK;
            doubleBlack.colour = Colour.BLACK;

            if(doubleBlack.key == null) {
                if (doubleBlack == doubleBlack.parent.left) {
                    doubleBlack.parent.left = null;
                } else {
                    doubleBlack.parent.right = null;
                }
                doubleBlack.parent = null;
            }
        }
        else{
            fixUpdDeletionCase5(doubleBlack);
        }
    }

    void fixUpdDeletionCase5(Node<T> doubleBlack){
        Node<T> sibling = Utils.getSibling(doubleBlack);

        if(sibling.colour == Colour.BLACK){
            if(doubleBlack == doubleBlack.parent.left && Utils.getNodesColour(sibling.right) == Colour.BLACK
            && Utils.getNodesColour(sibling.left) == Colour.RED){
                sibling.colour = Colour.RED;
                sibling.left.colour = Colour.BLACK;
                Utils.rotateRight(sibling, this);
            }
            else if(doubleBlack == doubleBlack.parent.right && Utils.getNodesColour(sibling.left) == Colour.BLACK
                    && Utils.getNodesColour(sibling.right) == Colour.RED){
                sibling.colour = Colour.RED;
                sibling.right.colour = Colour.BLACK;
                Utils.rotateLeft(sibling, this);
            }
        }

        fixUpDeletionCase6(doubleBlack);
    }

    void fixUpDeletionCase6(Node<T> doubleBlack){
        Node<T> sibling = Utils.getSibling(doubleBlack);

        sibling.colour = doubleBlack.parent.colour;
        doubleBlack.parent.colour = Colour.BLACK;

        if(doubleBlack == doubleBlack.parent.left){
            if(sibling.right != null){
                sibling.right.colour = Colour.BLACK;
            }
            Utils.rotateLeft(doubleBlack.parent, this);
        }
        else{
            if(sibling.left != null){
                sibling.left.colour = Colour.BLACK;
            }
            Utils.rotateRight(doubleBlack.parent, this);
        }

        fixUpDoubleBlackNode(doubleBlack);
    }

    // called after a terminal case 4 or 6 have been successfully
    // run
    //
    // if the double black node is supposed to be a null
    // then this makes sure that happens correctly,
    // otherwise makes sure that the colour is set to black
    // as should be the case after being a double black node
    void fixUpDoubleBlackNode(Node<T> doubleBlack){
        if(doubleBlack.key == null) {
            if (doubleBlack == doubleBlack.parent.left) {
                doubleBlack.parent.left = null;
            } else {
                doubleBlack.parent.right = null;
            }
            doubleBlack.parent = null;
        }

        doubleBlack.colour = Colour.BLACK;
    }

    // delete a node that has maximum of one child
    // if the node is red, we can either delete it completely
    // or if it has a child, move the childs key and value into the node
    // and re-colour it black
    //
    // otherwise if the node is black and it has no child we make it a double black
    // node by settings its key to null, if it has a child then it acts the same as a red
    // node
    Node<T> deleteNode(Node<T> node){

        // node to be deleted has a left node, move the left nodes
        // key and value into node and remove it
        if(node.left != null && node.right == null){
            node.left.colour = Colour.BLACK;
            node.key = node.left.key;
            node.value = node.left.value;
            node.left = null;

            return node;
        }
        // node to be deleted has a right node, move the right nodes
        // key and value into node and remove it
        else if(node.right != null && node.left == null) {
            node.right.colour = Colour.BLACK;
            node.key = node.right.key;
            node.value = node.right.value;
            node.right = null;

            return node;
        }
        else if(node.colour == Colour.RED){
            if(node == node.parent.left){
                node.parent.left = null;
            }
            else{
                node.parent.right = null;
            }

            node.parent = null;

            return null;
        }
        else{
            // this only happens in the case of a double black
            node.key = null;
            return node;
        }
    }


    // delete a node that has maximum of one child
    // if doubleBlack is true, then if both children of
    Node<T> deleteNode(Node<T> node, boolean doubleBlack){

        if(node.left != null && node.right == null){
            node.value = node.left.value;
            node.left.parent = null;
            node.left = null;
        }
        else if(node.right != null && node.left == null) {
            node.key = node.right.key;
            node.value = node.right.value;
            node.right.parent = null;
            node.right = null;
        }
        else{
            //TODO: clean up this code
            if(doubleBlack){
                node.key = null;
                return node;
            }



            node.parent = null;

            return null;
        }

        if(doubleBlack) {
            node.key = null;
        }
        else{
            node.colour = Colour.BLACK;
        }
        return node;
    }


}

