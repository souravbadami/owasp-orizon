/* Generated by: FreeCC 0.9.3. Do not edit. TypeDeclaration.java */
package org.owasp.orizon.mirage.java.parser;

import java.util.*;
public class TypeDeclaration extends BaseNode {
    public String getName() {
        for(Node n : children) {
            if (n.getId()==IDENTIFIER) {
                return n.toString();
            }
        }
        throw new RuntimeException("Should never get here.");
    }

    public boolean getInterface() {
        for(Node n : children) {
            if (n.getId()==INTERFACE) {
                return true;
            }
        }
        return false;
    }

    public String getPackageName() {
        String packageName=null;
        if (parent instanceof CompilationUnit) {
            CompilationUnit jcu=(CompilationUnit) parent;
            packageName=jcu.getPackageName();
        }
        return packageName;
    }

    public String getFullName() {
        String name=getName();
        String packageName=getPackageName();
        if (packageName!=null&&packageName.length()>0) {
            return packageName+"."+name;
        }
        return name;
    }

    public TypeParameterList getTypeParameterList() {
        ListIterator<Node>iterator=iterator();
        Node n=iterator.next();
        while (n.getId()!=IDENTIFIER) {
            n=iterator.next();
        }
        n=iterator.next();
        if (n instanceof TypeParameterList) {
            return(TypeParameterList) n;
        }
        return null;
    }

    public ClassOrInterfaceBody getBody() {
        return Nodes.firstChildOfType(this,ClassOrInterfaceBody.class);
    }

    public ExtendsList getExtendsList() {
        return Nodes.firstChildOfType(this,ExtendsList.class);
    }

    public ImplementsList getImplementsList() {
        return Nodes.firstChildOfType(this,ImplementsList.class);
    }

    public CompilationUnit getCompilationUnit() {
        Node parent=getParent();
        if (parent instanceof CompilationUnit) {
            return(CompilationUnit) parent;
        }
        return null;
    }

    public List<ImportDeclaration>getImportDeclarations() {
        CompilationUnit jcu=getCompilationUnit();
        if (jcu==null) {
            return new ArrayList<ImportDeclaration>();
        }
        return jcu.getImportDeclarations();
    }

    public void addElements(List<ClassOrInterfaceBodyDeclaration>elements) {
        Set<String>keys=new HashSet<String>();
        for(ClassOrInterfaceBodyDeclaration decl : elements) {
            String key=decl.getFullNameSignatureIfMethod();
            if (key!=null) {
                keys.add(key);
            }
        }
        for(Iterator<Node>it=getBody().iterator(); 
        it.hasNext(); 
        ) {
            Node n=it.next();
            if (n instanceof ClassOrInterfaceBodyDeclaration) {
                String s=((ClassOrInterfaceBodyDeclaration) n).getFullNameSignatureIfMethod();
                if (keys.contains(s)) {
                    it.remove();
                }
            }
        }
        getBody().prepend(elements);
    }

    public boolean isClass() {
        for(Node n : children) {
            if (n.getId()==CLASS) {
                return true;
            }
        }
        return false;
    }

    public void addImplements(ObjectType type) {
        ImplementsList implementsList=getImplementsList();
        if (implementsList==null) {
            implementsList=new ImplementsList();
            ListIterator<Node>iterator=iterator();
            while (iterator.hasNext()) {
                Node node=iterator.next();
                if (node instanceof ClassOrInterfaceBody||node instanceof EnumBody) break;
            }
            iterator.previous();
            iterator.add(implementsList);
        }
        implementsList.addType(type);
    }

    public void addExtends(ObjectType type) {
        ExtendsList extendsList=getExtendsList();
        if (extendsList==null) {
            extendsList=new ExtendsList();
            ListIterator<Node>iterator=iterator();
            while (iterator.hasNext()) {
                Node node=iterator.next();
                if (node instanceof ImplementsList||node instanceof ClassOrInterfaceBody) {
                    break;
                }
            }
            //            iterator.previous();
            //            iterator.add(Token.newToken(WHITESPACE, " "));
            //            iterator.next();
            iterator.add(extendsList);
        }
        extendsList.addType(type,getInterface());
    }

    public TypeDeclaration(int id) {
        super(id);
    }

    public TypeDeclaration() {
        super(JavaConstants.TYPEDECLARATION);
    }

}
