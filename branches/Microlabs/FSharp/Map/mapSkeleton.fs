// Mike Dusenberry
// F# Map Exercise
// 07/27/12
//

// functions to test
let square n = n * n;
let cube n = n * n * n;
let incr n = n + 1;
let decr n = n - 1;

//<end!TopSection>
// Write a rule to map a function on a list
//  -Note: we will provide the functions, such
//      as "square" to test your map function
//      with
//
// ex:
//  map square [4;3;2;1]; returns [16;9;4;1]
//



//<end!MidSection>
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
    let funcString = args.[1];
    let stringList = List.ofArray(args.[2].Split([|','|]));
    let intList = List.map(fun x -> int x) stringList;
    let mutable func = square;
    if (System.String.Equals(funcString, "cube", System.StringComparison.CurrentCultureIgnoreCase)) then
        func <- cube;
    else if (System.String.Equals(funcString, "increment", System.StringComparison.CurrentCultureIgnoreCase)) then
        func <- incr;
    else if (System.String.Equals(funcString, "decrement", System.StringComparison.CurrentCultureIgnoreCase)) then
        func <- decr;
    let ans = map func intList;
    printfn "%A" ans;
