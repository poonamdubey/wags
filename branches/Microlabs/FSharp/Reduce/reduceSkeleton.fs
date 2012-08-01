// Mike Dusenberry
// F# Reduce Exercise
// 07/30/12
//
//<end!TopSection>
// Write a rule to reduce a list to a single value 
//  using a given function
//  -Note: we will provide the operators, such
//      as addition (represented as "(+)") to test your 
//      reduce function with
//
// ex:
//  reduce (+) 0 [1;2;3;4]; returns 10
//  reduce (*) 1 [1;2;3]; returns 6
//



//<end!MidSection>
//<start!HiddenSection>
// Test code
//  - Get passed in operator and list (1st arg is the executable name), 
//    split on commas to create a list of string values, then convert 
//    each string to an int, to create a list of integers
//      - Will fail if given non-integers in list
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 3 then // need a operator and list passed in
    let opString = args.[1];
    let stringList = List.ofArray(args.[2].Split([|','|]));
    let intList = List.map(fun x -> int x) stringList;
    let mutable value = 0;
    let mutable operator = (+); // default is addition
    if (System.String.Equals(opString, "subtraction", System.StringComparison.CurrentCultureIgnoreCase)) then
        operator <- (-);
    else if (System.String.Equals(opString, "multiplication", System.StringComparison.CurrentCultureIgnoreCase)) then
        operator <- (*);
        value <- 1; // starting value needs to be 1 to be useful with multiplication
    let ans = reduce operator value intList;
    printfn "%d" ans;
