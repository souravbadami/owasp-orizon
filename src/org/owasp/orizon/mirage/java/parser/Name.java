/* Generated by: FreeCC 0.9.3. Do not edit. Name.java */
package org.owasp.orizon.mirage.java.parser;

public class Name extends BaseNode {
    public String toString() {
        StringBuilder buf=new StringBuilder();
        for(Token tok : Nodes.getRealTokens(this)) {
            buf.append(tok);
        }
        return buf.toString();
    }

    public Name(int id) {
        super(id);
    }

    public Name() {
        super(JavaConstants.NAME);
    }

}