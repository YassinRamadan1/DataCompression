public class Tag {
    public int position, length;
    public char nextSymbol;

    public Tag(){
        position = -1;
        length = -1;
        nextSymbol = '_';
    }

    public Tag(int position, int length, char nextSymbol) {
        this.position = position;
        this.length = length;
        this.nextSymbol = nextSymbol;
    }
}
