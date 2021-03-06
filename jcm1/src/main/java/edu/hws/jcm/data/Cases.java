/**************************************************************************
* Copyright (c) 2001, 2005 David J. Eck                                   *
*                                                                         *
* Permission is hereby granted, free of charge, to any person obtaining   *
* a copy of this software and associated documentation files (the         *
* "Software"), to deal in the Software without restriction, including     *
* without limitation the rights to use, copy, modify, merge, publish,     *
* distribute, sublicense, and/or sell copies of the Software, and to      *
* permit persons to whom the Software is furnished to do so, subject to   *
* the following conditions:                                               *
*                                                                         *
* The above copyright notice and this permission notice shall be included *
* in all copies or substantial portions of the Software.                  *
*                                                                         *
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,         *
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF      *
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  *
* IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY    *
* CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,    *
* TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE       *
* SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                  *
*                                                                         *
* ----                                                                    *
* (Released under new license, April 2012.)                               *
*                                                                         *
*             David J. Eck                                                *
*             Department of Mathematics and Computer Science              *
*             Hobart and William Smith Colleges                           *
*             300 Pulteney Street                                         *
*             Geneva, NY 14456                                            *
*             eck@hws.edu                                                 *
*             http://math.hws.edu/eck                                     *
**************************************************************************/

package edu.hws.jcm.data;

/**
 * An object of type Cases stores a list of "case values" that is generated
 * while an expression is being evaluated using the routine Expression.getValuesWithCases().
 * This information can be used as a heuristic (i.e. a fudge) to help detect
 * a possible discontinuity between two evaluations of the expression.  Suppose
 * that the expression is evaluated twice, with some change of variable values
 * between the two evaluations.  If the variables' values are not changed too much,
 * and if the Cases objects generated by the two evaluations are equal (as determined
 * by the "equals" method defined in this class), then it is likely that
 * there is no discontinuity.  (However, this is not perfect.  The discontinuity
 * in 1/x^2 won't be detected since the case value generated by 1/f(x) only
 * checks the sign of f(x), and the denominator of 1/x^2 is positive on both
 * sides of x=0.  If you want to be more paranoid, check both the expression
 * and its derivative.)  (I really don't like this very much, but it can be used to draw
 * pretty good graphs in general.)
 */
public class Cases {  
   private int[] cases = new int[1];  // Array of values that have been added with addCase(value).
   private int caseCt;                // Number of items stored in cases array.

   /**
    * Remove all the cases that have been added with addCase().
    * This makes it possible to reuse this object in another
    * call to Expression.getValueWithCases().
    */
   public void clear() {
     caseCt = 0;
   }

   /**
    * Add a new case value to the list stored in this object.
    */
   public void addCase(int value) {
     if (caseCt == cases.length) {
        int[] temp = new int[2*caseCt];
        System.arraycopy(cases,0,temp,0,caseCt);
        cases = temp;
     }
     cases[caseCt++] = value;
   }

   /**
    * Test whether c contains exactly the same list of case
    * values as this Cases object does.
    */
   public boolean equals(Cases c) {
     if (c.caseCt != caseCt)
        return false;
     for (int i = 0; i < caseCt; i++)
        if (c.cases[i] != cases[i])
           return false;
     return true;
   }

} // end class Cases
