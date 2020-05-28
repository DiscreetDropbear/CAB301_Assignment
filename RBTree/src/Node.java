public class Node <T>{
    String key = null;
    T value = null;
    Node<T> left = null;
    Node<T> right = null;
    Node<T> parent = null;
    Colour colour = Colour.RED;

    Node(String key, T value){
        this.key = key;
        this.value = value;
    }
}
