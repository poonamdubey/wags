// Mike Dusenberry
// F# Summation Exercise
// 07/14/12
//

// Write a rule to sum a list of ints
//
// ex:
//  summation [1..6]; returns 21
//
let summation lst = List.fold (+) 0 lst;


// Test code
//  - Get passed in list (will be the 2nd arg, since 1st arg is 
//    the executable name), split on commas to create list of string
//    values, then convert each string to an int, to create a list
//    of integers
//      - Will fail if given non-integers
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 2 then // need a list passed in
    let stringList = List.ofArray(args.[1].Split([|','|]));
    let intList = List.map(fun x -> int x) stringList;
    let ans = summation intList;
    printfn "%d" ans;
