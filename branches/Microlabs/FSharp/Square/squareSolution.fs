// Mike Dusenberry
// F# Exercises
// 07/11/12
//

// Write a rule to square a given integer
//
// ex:
//  square 6; returns 36
//
let square x = x * x;


// Test code
//  - Access the passed-in value, and cast as an int
//  - Call rule
//  - Print value
let value = int (System.Environment.GetCommandLineArgs().[1]);
let ans = square value;
printfn "%d" ans;

