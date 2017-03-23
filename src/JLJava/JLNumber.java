/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJava;

/**
 *
 * @author jl
 */
public class JLNumber {
  public static boolean isNumeric(String s1) {
    try {
      Double.parseDouble(s1);
      return true;
    } catch (NumberFormatException|NullPointerException e) { // s1 can not be parsed as double, or s1 is null
      return false;
    }
  }
}
