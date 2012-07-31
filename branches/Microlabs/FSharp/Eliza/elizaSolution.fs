// Mike Dusenberry
// F# Eliza Exercise
// 07/31/12
//
open System.Text.RegularExpressions

// Given Match active pattern
let (|Match|_|) (pat:string) (inp:string) =
    let m = Regex.Match(inp, pat)
    if m.Success
        then Some (List.tail [ for g in m.Groups -> g.Value ])
    else None

// Write a rule, matchInput, to implement the
//  input and response patterns of the Eliza program
//  to return meaningful statements of conversation based
//  on the input string.
//  
//  -Note: We will provide the active pattern "Match",
//      which can be used with the builtin F# match expressions
//      to capture part of an input string using a regular
//      expression.
//
//  -Hints:
//      -You will need to use the builtin F# match expression, along
//       with our "Match" active pattern
//      -You will need to design regular expressions that capture part
//       of the input
//      -Submit and use the review tab to determine the types of input
//       your Eliza program should recognize and repsond to.
//      -Your Eliza program should respond with "In what way?" for any
//       unrecognized input
//      -Your Eliza program should be able to respond to three specific
//       input sentences, in addition to the response to unrecognized
//       inputs
//      -Make your regular expressions case-insensitive by inserting "(?i)"
//       at the beginning of the expression
//      
//
// ex: 4 types of input listed below:
//  
//  I am tired
//  "I am sorry to hear you are tired
//
//  My major makes me happy
//  "Tell me about your major"
//
//  Can i talk with you longer?
//  "Yes you can talk with me"
//
//  You won't recognize this
//  "In what way?"
//
let matchInput (sentence:string) =
    match sentence with
    | Match "(?i)^.*i\s+am\s+(.*)$" result ->
        "I am sorry to hear you are " + (List.nth result 0).ToString()
    | Match "(?i)^.*my\s+(\w+)\s+.*me.*$" result ->
        "Tell me about your " + (List.nth result 0).ToString()
    | Match "(?i)^.*can\s+i\s+(.*)\s+with\s+you.*$" result ->
        "Yes you can " + (List.nth result 0).ToString() + " with me"
    | _ -> "In what way?"



// Test code
//  - Get passed in operator and list (1st arg is the executable name), 
//    split on commas to create a list of string values, then convert 
//    each string to an int, to create a list of integers
//      - Will fail if given non-integers in list
//  - Call rule
//  - Print value
let args = System.Environment.GetCommandLineArgs();
if args.Length >= 2 then // need a string passed in
    let mutable sentence = args.[1];
    // Java has a bug as noted in the test file, so we have to replace 
    //  all underscores with spaces here
    sentence <- sentence.Replace("_", " ");
    let ans = matchInput sentence;
    printfn "%s" ans;
