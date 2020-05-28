import java.util.ArrayList;
import java.util.Deque;

/**
 * all the helper functions that are used
 */
public class Utils {


    static<T> boolean validateTree(RBTree<T> tree){
        return validateTree(tree.root, 0, 0, null) != -1;
    }

    static<T> int validateTree(Node<T> node, int firstPathBlacks, int thisPathBlacks, Colour lastColour){

        if(node == null){
            // reached  leaf node
            if(firstPathBlacks == 0){
                // finish traversing the first path, now return the number of blacks
                // for the
                return thisPathBlacks + 1;
            }
            else{
                thisPathBlacks += 1;
                if(firstPathBlacks != thisPathBlacks){
                    // not all paths have the same number of blacks
                    // return -1 to signify failure
                    return -1;
                }

                return firstPathBlacks;
            }
        }

        if(node.colour == Colour.BLACK){
            thisPathBlacks += 1;
        }

        if(lastColour == Colour.RED && node.colour != Colour.BLACK){
            // violates the rule that all red nodes children must be black                    System.out.println("firstPathBlacks = " + firstPathBlacks);
            return -1;
        }


        // traverse to the left
        firstPathBlacks = validateTree(node.left, firstPathBlacks,  thisPathBlacks, node.colour);
        if(firstPathBlacks == -1){
            return -1;
        }

        // traverse to the right
        firstPathBlacks = validateTree(node.right, firstPathBlacks, thisPathBlacks, node.colour);
        if(firstPathBlacks == -1){
            return -1;
        }

        if(node.parent == null){
            // final return from the root node
            return 1;
        }

        return firstPathBlacks;
    }

    static<T> Node<T> getParent(Node<T> node){

        if(node == null){
            return null;
        }

        return node.parent;
    }

    static<T> Node<T> getGrandParent(Node<T> node){

        if(node == null){
            return null;
        }

        return getParent(getParent(node));
    }

    /*
     */
    static<T> Node<T> getUncle(Node<T> node){
        // get parent so we can find if its to the
        // left or right of grandParent so we know to
        // return the opposite side
        Node<T> parent = getParent(node);

        Node<T> grandParent = getGrandParent(node);
        if(grandParent == null){
            return null;
        }

        if(parent.equals(grandParent.left)){
            return grandParent.right;
        }else{
            return grandParent.left;
        }
    }

    static<T> Node<T> getSibling(Node<T> node){

        if(node == null){
            return null;
        }

        Node<T> parent = getParent(node);

        if(parent == null){
            return null;
        }

        if(parent.left == node){
            if(parent.right == null){
                return null;
            }

            return parent.right;
        }
        else{
            if(parent.left == null){
                return null;
            }

            return parent.left;
        }
    }

    static<T> Colour getNodesColour(Node<T> node){
        if(node == null){
            return Colour.BLACK;
        }

        return node.colour;
    }

    static<T> Colour getSingleChildColour(Node<T> node){
        if(node.left != null && node.right == null){
            return node.left.colour;
        }
        else if(node.right != null && node.left == null){

            return node.right.colour;
        }

        return Colour.BLACK;
    }


    /*
     *       (a)                    c
     *      /  \                   / \
     *     b    c                (a)  e
     *         /  \     ->       / \
     *        d    e            b   d
     *      /  \                   /  \
     *     f   g                  f    g
     *
     */
    static<T> void rotateLeft(Node<T> nodeA, RBTree<T> tree){
        if(nodeA == null){
            System.exit(-1);
        }

        if(nodeA.right == null){
            // the right child of nodeA is non-existent
            // this should be impossible
            System.exit(-1);
        }

        // take a reference to c before overwriting a's right pointer
        Node<T>  nodeC = nodeA.right;
        // node a's right pointer now points to node d
        nodeA.right = nodeC.left;

        // if node d isn't null, then make a the new parent of node d
        if(nodeA.right != null){
            nodeA.right.parent = nodeA;
        }

        // now the node that was in c's left pointer has been moved to a,
        // we can set c's left pointer to point at a
        nodeC.left = nodeA;

        // swap the parents around
        nodeC.parent = nodeA.parent;
        nodeA.parent = nodeC;

        // make set nodeB's new parent to point to it
        if(nodeC.parent != null){
            if(nodeA.equals(nodeC.parent.left)){
                nodeC.parent.left = nodeC;
            }
            else{
                nodeC.parent.right = nodeC;
            }
        }else{
            tree.root = nodeC;
        }

    }

    /*
     *  rotates the last node in the path to the right this case (a) is the
     *  last node in the path
     *
     *        (a)                 b
     *       /  \               /  \
     *     b     c             d   (a)
     *    /  \       ->            /  \
     *   d   e                    e     c
     *      /  \                 /  \
     *     f   g                f   g
     *
     *
     */
    static<T> void rotateRight(Node<T> nodeA, RBTree<T> tree){
        if(nodeA == null){
            System.exit(-1);
        }

        if(nodeA.left == null){
            // left child of node being rotated right is non-existent
            // this should be impossible
            System.exit(-1);
        }
        // take a new reference to b before we overwrite a's left pointer
        Node<T> nodeB = nodeA.left;
        // a's left pointer now points to node e in the diagram
        nodeA.left = nodeB.right;

        // set node d to point to node a as parent
        if(nodeA.left != null){
            nodeA.left.parent = nodeA;
        }

        // now b's right pointer has been moved we can set b's right pointer to point to a
        nodeB.right = nodeA;

        // swap the parents around
        nodeB.parent = nodeA.parent;
        nodeA.parent = nodeB;

        // make set nodeB's new parent to point to it
        if(nodeB.parent != null){
            if(nodeA.equals(nodeB.parent.left)){
                nodeB.parent.left = nodeB;
            }
            else{
                nodeB.parent.right = nodeB;
            }
        }
        else{
            tree.root = nodeB;
        }
    }

    static<T> Node<T> getSuccessor(Node<T> node){

        if(node.right == null){
            if(node.parent == null){
                // on root node and there are no more nodes
                // to go to
                return null;
            }

            // if this node is left of parent, parent
            // is the next node
            if(node.parent.left == node){
                node = node.parent;
            }
            // this node is right of parent go up until
            // the node is left of the parent or we reach the root
            else{
                while(node.parent != null){
                    if(node.parent.left == node){
                        // the parent of this node is the next successor
                        // set the node to the parent then this will be returned
                        node = node.parent;
                        break;
                    }
                    else{
                        // parent is the root node and we are coming from the right
                        // so there are no more successors return null;
                        if(node.parent.parent == null && node == node.parent.right){
                            return null;
                        }
                        else{
                            // the parent of this node is the predecessor
                            // set this node to be equal to its parent to continue
                            // the ascent up the tree
                            node = node.parent;
                        }
                    }
                }

            }

        }
        else{

            node = node.right;
            while(node.left != null){
                node = node.left;
            }
        }

        return node;
    }

    // return the predecessor of node or null if there isn't one
    static<T> Node<T> getPredecessor(Node<T> node){

        if(node.left == null){
            return null;
        }

        node = node.left;

        while(true){
            if(node.right != null){
                node = node.right;
            }
            else{
                return node;
            }
        }


    }
}
