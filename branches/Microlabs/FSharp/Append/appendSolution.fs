// Mike Dusenberry
// F# Append Exercise
// 07/19/12
//

// Write a rule to append two lists
//
// ex:
//  append [1;2;3] [4;5;6]; returns [1;2;3;4;5;6]
//
let rec append lst1 lst2 = 
    if lst1 = [] then lst2
    else    let head = List.head lst1
            let tail = List.tail lst1
            let r = append tail lst2
            head::r;


// Test code
//  - Get passed in lists (1st arg is the executable name), split 
//    on commas to create list of string values
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 3 then // need two lists passed in
    let list1 = List.ofArray(args.[1].Split([|','|]));
    let list2 = List.ofArray(args.[2].Split([|','|]));
    let ans = append list1 list2;
    printfn "%A" ans;
