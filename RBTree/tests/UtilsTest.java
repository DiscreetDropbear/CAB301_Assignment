import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void getParent() {
        /*
         *  We are at the node in parentheses and are trying to get the node with
         * the asterix * next to it
         *       a
         *      / \
         *     b*  c
         *   /  \
         * (d)    e
         *
         */
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 3);
        Node<Integer> c = new Node<>("c", 2);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);

        a.left = b;
        b.parent = a;

        a.right = c;
        c.parent = a;

        b.left = d;
        d.parent = b;

        b.right = e;
        e.parent = b;


        Node<Integer> parent = Utils.getParent(d);
        assertNotNull(parent);
        assertEquals(parent, b);

        /*
         *  We are at the root, there is no parent, we should get null back
         *      (a)
         *      / \
         *     b   c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getParent(a));
    }

    @Test
    void getGrandParent() {
        /*
         *  We are at the node in parentheses and are trying to get the node with
         * the asterix * next to it
         *       a*
         *      / \
         *     b   c
         *   /  \
         * (d)    e
         *
         */
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 3);
        Node<Integer> c = new Node<>("c", 2);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);

        a.left = b;
        b.parent = a;

        a.right = c;
        c.parent = a;

        b.left = d;
        d.parent = b;

        b.right = e;
        e.parent = b;

        Node<Integer> grandParent = Utils.getGrandParent(d);
        assertNotNull(grandParent);
        assertEquals(grandParent, a);

        /*
         *  We are at the root, there is no uncle, we should get null back
         *      (a)
         *      / \
         *     b   c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getGrandParent(a));

        /*
         *  We are at b, there is no uncle, we should get null back
         *       a
         *      / \
         *    (b)  c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getGrandParent(b));
    }

    @Test
    void getUncle() {
        /*
         *  We are at the node in parentheses and are trying to get the node with
         * the asterix * next to it
         *       a
         *      / \
         *     b   c*
         *   /  \
         * (d)    e
         *
         */
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 3);
        Node<Integer> c = new Node<>("c", 2);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);

        a.left = b;
        b.parent = a;

        a.right = c;
        c.parent = a;

        b.left = d;
        d.parent = b;

        b.right = e;
        e.parent = b;


        Node<Integer> uncle = Utils.getUncle(d);
        assertNotNull(uncle);
        assertEquals(uncle, c);

        /*
         *  We are at the root, there is no uncle, we should get null back
         *      (a)
         *      / \
         *     b   c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getUncle(a));

        /*
         *  We are at b, there is no uncle, we should get null back
         *      (a)
         *      / \
         *     b   c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getUncle(b));
    }

    @Test
    void getSibling(){
        /*
         *  We are at the node in parentheses and are trying to get the node with
         * the asterix * next to it
         *       a
         *      / \
         *     b   c
         *   /  \
         * (d)    e*
         *
         */
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 3);
        Node<Integer> c = new Node<>("c", 2);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);

        a.left = b;
        b.parent = a;

        a.right = c;
        c.parent = a;

        b.left = d;
        d.parent = b;

        b.right = e;
        e.parent = b;


        Node<Integer> sibling = Utils.getSibling(d);
        assertNotNull(sibling);
        assertEquals(sibling, e);

        /*
         *  We are at the root, there is no sibling, we should get null back
         *      (a)
         *      / \
         *     b   c
         *   /  \
         *  d    e
         *
         */

        assertNull(Utils.getSibling(a));

        /*
         *  We are at b, there is no sibling, we should get null back
         *      (a)
         *      /
         *     b
         *   /  \
         *  d    e
         *
         */
        a.right = null;
        assertNull(Utils.getSibling(b));
    }

    @Test
    void rotateLeft() {

        RBTree<Integer> tree = new RBTree<>();
         /*
         *       (a)                    c
         *      /  \                   / \
         *     b    c                (a)  e
         *          /  \    ->       / \
         *         d    e           b   d
         *      /  \                   /  \
         *     f   g                  f    g
         *
         */
        //note the nodes will not be in a valid BST based on the keys, this is just
        // testing the rotations which don't rely on the keys
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 2);
        Node<Integer> c = new Node<>("c", 3);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);
        Node<Integer> f = new Node<>("f", 6);
        Node<Integer> g = new Node<>("g", 7);

        a.parent = null;
        a.left = b;
        b.parent = a;
        a.right = c;
        c.parent = a;

        c.left = d;
        d.parent = c;
        c.right = e;
        e.parent = c;

        d.left = f;
        f.parent = d;
        d.right = g;
        g.parent = d;

        Utils.rotateLeft(a, tree);

        assertNull(c.parent);
        assertEquals(c.left, a);
        assertEquals(a.parent, c);

        assertEquals(c.right, e);
        assertEquals(e.parent, c);

        assertEquals(a.left, b);
        assertEquals(b.parent, a);

        assertEquals(a.right, d);
        assertEquals(d.parent, a);

        assertEquals(d.left, f);
        assertEquals(f.parent, d);

        assertEquals(d.right, g);
        assertEquals(g.parent, d);
        /*
         *       (a)                    c
         *      /  \                   / \
         *     b    c                (a)  e
         *          /  \    ->       / \
         *         d    e           b   d
         *      /  \                   /  \
         *     f   g                  f    g
         *
         */
        /*
         *        a                a
         *       /                /
         *     (b)     ->        c
         *       \              /
         *        c            (b)
         */

        // reset all the nodes
        a = new Node<>("a", 1);
        b = new Node<>("b", 2);
        c = new Node<>("c", 3);

        a.left = b;
        b.parent = a;

        b.right = c;
        c.parent = b;

        Utils.rotateLeft(b, tree);

        assertEquals(a.left, c);
        assertEquals(c.parent, a);

        assertEquals(c.left, b);
        assertEquals(b.parent, c);
    }

    @Test
    void rotateRight() {

        RBTree<Integer> tree = new RBTree<>();
        /*
         *        (a)                 b`
         *       /  \               /  \
         *     b     c             d`   (a`)
         *    /  \       ->            /  \
         *   d   e                    e`     c`
         *      /  \                 /  \
         *     f   g                f`   g`
         *
         *
         */
        //note the nodes will not be in a valid BST based on the keys, this is just
        // testing the rotations which don't rely on the keys
        Node<Integer> a = new Node<>("a", 1);
        Node<Integer> b = new Node<>("b", 2);
        Node<Integer> c = new Node<>("c", 3);
        Node<Integer> d = new Node<>("d", 4);
        Node<Integer> e = new Node<>("e", 5);
        Node<Integer> f = new Node<>("f", 6);
        Node<Integer> g = new Node<>("g", 7);

        a.parent = null;
        a.left = b;
        a.right = c;
        b.parent = a;
        c.parent = a;

        b.left = d;
        b.right = e;
        d.parent = b;
        e.parent = b;

        e.left = f;
        e.right = g;
        f.parent = e;
        g.parent = e;

        Utils.rotateRight(a, tree);

        assertNull(b.parent);
        assertEquals(b.left, d);
        assertEquals(d.parent, b);

        assertEquals(b.right, a);
        assertEquals(a.parent, b);

        assertEquals(a.left, e);
        assertEquals(e.parent, a);

        assertEquals(a.right, c);
        assertEquals(c.parent, a);

        assertEquals(e.left, f);
        assertEquals(f.parent, e);

        assertEquals(e.right, g);
        assertEquals(g.parent, e);

        /*
         *        a                a
         *         \                \
         *          (b)   ->         c
         *         /                  \
         *        c                    (b)
         */

        // reset all the nodes
        a = new Node<>("a", 1);
        b = new Node<>("b", 2);
        c = new Node<>("c", 3);

        a.right = b;
        b.parent = a;

        b.left = c;
        c.parent = b;

        Utils.rotateRight(b, tree);

        assertEquals(a.right, c);
        assertEquals(c.parent, a);

        assertEquals(c.right, b);
        assertEquals(b.parent, c);
    }


    @Test
    void validateTree() {
        RBTree<Integer> tree = new RBTree<>();

        /*
            invalid tree, red nodes are marked with *
            there is a red node with a red child!
                a
              /  \
            *b    *c
            / \    / \
           d   e  *f  g

         */

        Node<Integer> a = new Node<>("a", 0);
        Node<Integer> b = new Node<>("b", 0);
        Node<Integer> c = new Node<>("c", 0);
        Node<Integer> d = new Node<>("d", 0);
        Node<Integer> e = new Node<>("e", 0);
        Node<Integer> f = new Node<>("f", 0);
        Node<Integer> g = new Node<>("g", 0);


        a.colour = Colour.BLACK;
        a.left = b;
        a.right = c;

        b.parent = a;
        b.left = d;
        b.right = e;

        c.parent = a;
        c.left = f;
        c.right = g;

        d.colour = Colour.BLACK;
        d.parent = b;

        e.colour = Colour.BLACK;
        e.parent = b;

        f.parent = c;

        g.colour = Colour.BLACK;
        g.parent = c;

        tree.root = a;

        assertFalse(Utils.validateTree(tree));

        /*
            invalid tree, red nodes are marked with *
            not all paths have the same number of blacks
                a
              /  \
            *b    *c
            / \
           d   e

         */

        c.left = null;
        c.right = null;

        assertFalse(Utils.validateTree(tree));
         /*
            Valid tree, red nodes are marked with *
                a
              /  \
            *b    *c
            / \   / \
           d   e f   g

         */
        c.left = f;
        f.colour = Colour.BLACK;
        c.right = g;

        assertTrue(Utils.validateTree(tree));
    }
    @Test
    void getSuccessor(){

        Node<Integer> a = new Node<>("a", 0);
        Node<Integer> b = new Node<>("b", 0);
        Node<Integer> c = new Node<>("c", 0);
        Node<Integer> d = new Node<>("d", 0);
        Node<Integer> e = new Node<>("e", 0);
        Node<Integer> f = new Node<>("f", 0);
        Node<Integer> g = new Node<>("g", 0);

        a.colour = Colour.BLACK;
        a.left = b;
        a.right = c;

        b.parent = a;
        b.left = d;
        b.right = e;

        c.parent = a;
        c.left = f;
        c.right = g;

        d.colour = Colour.BLACK;
        d.parent = b;

        e.colour = Colour.BLACK;
        e.parent = b;

        f.colour = Colour.BLACK;
        f.parent = c;

        g.colour = Colour.BLACK;
        g.parent = c;

        /*
            the node with parentheses is the one we are starting at
            the nodes with the astrix is the one we should get after
            calling getSuccessor()
                a
              /   \
            *b     c
            / \   / \
          (d)   e f   g

           b is the successor of d
         */
        assertEquals(Utils.getSuccessor(d), b);


        /*
            the node with parentheses is the one we are starting at
            the nodes with the astrix is the one we should get after
            calling getSuccessor()
                a
              /   \
            (b)     c
            / \   / \
           d  *e f   g

           e is the successor of b
         */
        assertEquals(Utils.getSuccessor(b), e);

        /*
            the node with parentheses is the one we are starting at
            the nodes with the astrix is the one we should get after
            calling getSuccessor()
               *a
              /   \
             b     c
            / \   / \
           d  (e) f   g

            a is the successor of e
         */
        assertEquals(Utils.getSuccessor(e), a);


        /*
            the node with parentheses is the one we are starting at
            the nodes with the astrix is the one we should get after
            calling getSuccessor()
               (a)
              /   \
             b     c
            / \   / \
           d   e *f   g

            f is the successor of a
         */
        assertEquals(Utils.getSuccessor(a), f);

        /*
            the node with parentheses is the one we are starting at
            the nodes with the astrix is the one we should get after
            calling getSuccessor()
                a
              /   \
             b     c
            / \   / \
           d   e f  (g)

            g has no successor we should get null
         */
        assertNull(Utils.getSuccessor(g));
    }

    /*
    getPredecessor
     */


}