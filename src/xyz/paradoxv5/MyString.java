package xyz.paradoxv5;
import org.jetbrains.annotations.*;
import java.util.*;

/**
  A {@link CharSequence} implementation backed by {@linkplain #chars a Character List}
  <p>
  The backing list determines whether the instance is mutable (i.e. writeable).
  
  @author
    <a href="https://github.com/ParadoxV5/">ParadoxV5</a>.
    <a href="https://www.boost.org/LICENSE_1_0.txt">Boost Software License 1.0</a>
  @version
    {@value #serialVersionUID}.0
*/
public class MyString implements CharSequence, Comparable<@NotNull CharSequence>, Cloneable, java.io.Serializable {
  @java.io.Serial private static final long serialVersionUID = 1;
  
  private @NotNull List<Character> chars;
  @Contract(value = "-> _", pure = true)
  public @NotNull List<Character> getChars() { return chars; }
  @Contract(mutates="this")
  public void setChars(@NotNull List<Character> chars) { this.chars = Objects.requireNonNull(chars); }
  @Contract(mutates="this")
  public MyString(@NotNull List<Character> chars) { this.chars = Objects.requireNonNull(chars); }
  
  @Contract(mutates="this") public MyString(@NotNull CharSequence charSeq) {
    this(charSeq instanceof MyString myString
      ? myString.getChars()
      : charSeq.chars().mapToObj(e -> Character.valueOf((char)e)).toList()
    );
  }
  @Contract(mutates="this") public MyString(char @NotNull... charArray) {
    this(String.valueOf(charArray));
  }
  
  @Contract(value = "_ -> _", pure = true) @Override
  public char charAt(int index) { return getChars().get(index).charValue(); }
  @Contract(value = "-> _", pure = true) @Override
  public int length() { return getChars().size(); }
  @Contract(value = "_, _ -> new", pure = true) @Override
  public MyString subSequence(int start, int end) { return new MyString(getChars().subList(start, end)); }
  @Contract(value = "_ -> _", pure = true) @Override
  public int compareTo(@NotNull CharSequence other) { return CharSequence.compare(this, other); }
  
  @Contract(value = "-> new", pure = true) public char @NotNull[] toCharArray() {
    List<Character> chars = getChars();
    char[] charArray = new char[chars.size()];
    @NotNull ListIterator<Character> iter = chars.listIterator();
    while(iter.hasNext())
      charArray[iter.nextIndex()] = iter.next().charValue();
    return charArray;
  }
  @Contract(value = "-> _", pure = true) @Override public @NotNull String toString() {
    return String.valueOf(toCharArray());
  }
  @Contract(value = "-> new", pure = true) public MyString deepClone() {
    return new MyString(new LinkedList<>(getChars()));
  }
  
  @Contract(value = "null -> false; !null -> _", pure = true) @Override
  public boolean equals(@Nullable Object obj) {
    return super.equals(obj)
      || obj instanceof MyString other
      && getChars().equals(other.getChars());
  }
  @Contract(value = "-> _", pure = true) @Override public int hashCode() {
    return getChars().hashCode();
  }
  @Contract(value = "-> new", pure = true) @Override
  public @NotNull MyString clone() throws CloneNotSupportedException { return (MyString)(super.clone()); }
}