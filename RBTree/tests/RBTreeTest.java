import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeTest {

    /*
       The tree will look like the following
           c
          / \
         b   g
            /
           d
     */
    @Test
    void internalInsert(){
        RBTree<Integer> tree = new RBTree<>();

        Node<Integer> c = new Node<Integer>("c", 1);
        tree.internalInsert(c, false);

        assertEquals(c, tree.root);
        assertEquals(c.colour, Colour.BLACK);

        Node<Integer> b = new Node<Integer>("b", 2);
        tree.internalInsert(b, false);
        assertEquals(b, c.left);

        Node<Integer> g = new Node<Integer>("g", 3);
        tree.internalInsert(g, false);
        assertEquals(g, c.right);

        Node<Integer> d = new Node<Integer>("d", 4);
        tree.internalInsert(d, false);
        assertEquals(d, g.left);
    }

    @Test
    void search(){
        RBTree<Integer> tree = new RBTree<>();

        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.insert("c", 3);
        tree.insert("d", 4);
        tree.insert("e", 5);
        tree.insert("f", 6);
        tree.insert("g", 7);

        assertEquals(5, tree.search("e"));

        assertEquals(1, tree.search("a"));

        assertNull(tree.search(null));

    }

    @Test
    void insert(){

        RBTree<Integer> tree = new RBTree<>();

        tree.insert("a", 2);
        tree.insert("b", 2);
        tree.insert("c", 2);
        tree.insert("d", 2);
        tree.insert("e", 2);
        tree.insert("f", 2);
        tree.insert("g", 2);
        tree.insert("h", 2);
        tree.insert("i", 2);
        tree.insert("j", 2);
        tree.insert("k", 2);
        tree.insert("l", 2);
        tree.insert("m", 2);

        assertTrue(Utils.validateTree(tree));
    }

    @Test
    void update(){
        RBTree<Integer> tree = new RBTree<>();

        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.insert("c", 3);
        tree.insert("d", 4);

        // update returns the last value correctly
        assertEquals(1, tree.update("a", 5));
        // update set the new value correctly
        assertEquals(5, tree.search("a"));

        // update doesn't succeed if node doesn't already exist
        assertNull(tree.update("g", 3));
    }

    @Test
    void delete(){

        RBTree<Integer> tree = new RBTree<>();

        tree.insert("a", 2);
        tree.insert("b", 2);
        tree.insert("c", 2);
        tree.insert("d", 2);
        tree.insert("e", 2);
        tree.insert("f", 2);
        tree.insert("g", 2);
        tree.insert("h", 2);
        tree.insert("i", 2);
        tree.insert("j", 2);
        tree.insert("k", 2);
        tree.insert("l", 2);
        tree.insert("m", 2);
        tree.insert("n", 2);
        tree.insert("o", 2);
        tree.insert("p", 2);
        tree.insert("q", 2);
        tree.insert("r", 2);
        tree.insert("s", 2);
        tree.insert("t", 2);
        tree.insert("u", 2);
        tree.insert("v", 2);
        tree.insert("w", 2);
        tree.insert("x", 2);
        tree.insert("y", 2);
        tree.insert("z", 2);

        assertTrue(Utils.validateTree(tree));

        tree.delete("i");
        assertTrue(Utils.validateTree(tree));

        tree.delete("n");
        assertTrue(Utils.validateTree(tree));

        tree.delete("o");
        assertTrue(Utils.validateTree(tree));

        tree.delete("q");
        assertTrue(Utils.validateTree(tree));

        tree.delete("d");
        assertTrue(Utils.validateTree(tree));

        tree.delete("u");
        assertTrue(Utils.validateTree(tree));

        tree.delete("c");
        assertTrue(Utils.validateTree(tree));

        tree.delete("k");
        assertTrue(Utils.validateTree(tree));

        tree.delete("y");
        assertTrue(Utils.validateTree(tree));

        tree.delete("w");
        assertTrue(Utils.validateTree(tree));

        tree.delete("f");
        assertTrue(Utils.validateTree(tree));

        tree.delete("e");
        assertTrue(Utils.validateTree(tree));

        tree.delete("x");
        assertTrue(Utils.validateTree(tree));

        tree.delete("s");
        assertTrue(Utils.validateTree(tree));

        tree.delete("h");
        assertTrue(Utils.validateTree(tree));

        tree.delete("a");
        assertTrue(Utils.validateTree(tree));

        tree.delete("t");
        assertTrue(Utils.validateTree(tree));

        tree.delete("r");
        assertTrue(Utils.validateTree(tree));

        tree.delete("b");
        assertTrue(Utils.validateTree(tree));

        tree.delete("j");
        assertTrue(Utils.validateTree(tree));

        tree.delete("l");
        assertTrue(Utils.validateTree(tree));

        tree.delete("z");
        assertTrue(Utils.validateTree(tree));

        tree.delete("m");
        assertTrue(Utils.validateTree(tree));

        tree.delete("v");
        assertTrue(Utils.validateTree(tree));

        tree.delete("p");
        assertTrue(Utils.validateTree(tree));

        tree.delete("g");
        assertTrue(Utils.validateTree(tree));

        assertNull(tree.root);

    }
}