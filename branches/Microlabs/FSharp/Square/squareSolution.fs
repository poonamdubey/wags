// Mike Dusenberry
// F# Square Exercise
// 07/11/12
//

// Write a rule to square a given integer
//
// ex:
//  square 6; returns 36
//
let square x = x * x;


// Test code
//  - Access the passed-in value (1st arg is the executable name), 
//    and cast as an int
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 2 then // need a value passed in
    let value = int (args.[1]);
    let ans = square value;
    printfn "%d" ans;

