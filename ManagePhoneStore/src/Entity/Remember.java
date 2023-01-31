/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Dell
 */
public class Remember {
    int CodeRemember;
    String UserNames,PassWord;

    public Remember() {
    }

    public Remember(int CodeRemember, String UserNames, String PassWord) {
        this.CodeRemember = CodeRemember;
        this.UserNames = UserNames;
        this.PassWord = PassWord;
    }

    public int getCodeRemember() {
        return CodeRemember;
    }

    public void setCodeRemember(int CodeRemember) {
        this.CodeRemember = CodeRemember;
    }

    public String getUserNames() {
        return UserNames;
    }

    public void setUserNames(String UserNames) {
        this.UserNames = UserNames;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }
    
}
