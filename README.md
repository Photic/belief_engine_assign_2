
- [Menu System](#menu-system)
  - [User Input Option](#user-input-option)
  - [Examples Option](#examples-option)
  - [Quit Program](#quit-program)
- [Known Working Sentences for Belief base](#known-working-sentences-for-belief-base)

# Menu System
When you first start up the program, you will be met with the menu system. This system needs user input to navigate the programs functionality.

```
         | Menu         | Type                                  | Options
         |              : Input Belief Base                     : 1 
         |              : Examples                              : 2
         |              : Quit Agent                            : q

         | Choose Menu Option: 
```

## User Input Option

The "Input Belief Base" option is where the user can input their own comma separated Belief Base. An example of this could be.
```
( alpha ,  alpha | !beta , ( !alpha & (beta | (!!gamma)) , alpha <=> (beta | gamma), !beta )
```

After doing so you will be met with what the Agent thinks is the Belief Base, and a set of new options on how you can manipulate the Belief Base. Remember to read "[Known Working Sentences for Belief base](#known-working-sentences-for-belief-base)" for more information about what our string parser can do.

## Examples Option

The examples section just holds pre programmed functions that perform the same tasks that you can perform in the user input section.

If for some reason your inputs fail, please see this sections for functioning code.

## Quit Program
No matter where you are in the menus, inputting q will quit the Agent completely. 

# Known Working Sentences for Belief base
Because we did not get to create a string translator for all possible sentence constructed strings. We need to set some guidelines for how you can input a sentence into the "Input Belief Base" Menu option and all corresponding input options. 

Below is a list of working sentences.
```
alpha
!alpha
alpha | beta
alpha & (beta | gamma)
```

It does not have to be these specific connections, you can use any combination of connectors ( !, |, &, =>, <=> ) as you want. What you cannot do is that is on the below list.

```
(beta | gamma) & alpha
(alpha | beta) <=> (beta | gamma)
```

Because of how we search through and reconstruct the string, a sentence like that would result in a Belief base looking like this.
```
beta | (gamma & alpha)
beta <=> (beta | gamma)
```
Respectively.
