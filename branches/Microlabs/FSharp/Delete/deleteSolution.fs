// Mike Dusenberry
// F# Delete Exercise
// 07/27/12
//

// Write a rule to delete a value from a list
//
// ex:
//  delete 1 [1;2;3]; returns [2;3]
//
let rec del target lst =
    match lst with
    | [] -> []
    | hd::tl ->
        let r = del target tl
        if hd = target then r
        else hd::r;


// Test code
//  - Get passed in value and list (1st arg is the executable name), split 
//    on commas to create list of string values
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 3 then // need two lists passed in
    let value = int (args.[1]);
    let stringList = List.ofArray(args.[2].Split([|','|]));
    let intList = List.map(fun x -> int x) stringList;
    let ans = del value intList;
    printfn "%A" ans;
