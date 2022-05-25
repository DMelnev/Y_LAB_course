/**
 * enum Encoding
 *
 * @author Melnev Dmitry
 * @version 2022-02-
 */
package Lesson_4.Data;

public enum Encoding {

    WINDOWS1251("windows-1251"),
    UTF8("UTF-8"),
    UTF16("UTF-16"),
    KOI8R("KOI8-R");

private String charset;
    Encoding(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}
