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

//<end!TopSection>
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
//      -Your Eliza program should be able to respond to four specific
//       input sentences, in addition to the response to unrecognized
//       inputs
//      -Make your regular expressions case-insensitive by inserting "(?i)"
//       at the beginning of the expression
//      
//
// ex: 5 types of input listed below:
//
//  My major makes me happy
//  "Tell me about your major"
//  
//  I am tired
//  "I am sorry to hear you are tired
//
//  I need to go to sleep
//  "Do you really need to go to sleep?"
//
//  Can i talk with you longer?
//  "Yes you can talk with me"
//
//  You won't recognize this
//  "In what way?"
//



//<end!MidSection>
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
