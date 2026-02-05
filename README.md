# Gwentstone-1v1-Card-Game
GwentStone is a turn-based card game backend inspired by Hearthstone, implemented in
Java with a strong focus on object-oriented design, extensibility, and clean separation
of responsibilities.

Originally developed as part of an Object-Oriented Programming course, the project
was designed to model a complete game engine capable of processing game actions,
managing state, and executing complex card and hero abilities.

---

## Features

- Turn-based gameplay for two players
- Deck, hand, and board management
- Hero and minion cards with distinct abilities
- Special ability minions and heroes
- Command-based action execution
- Dynamic game state querying (cards on board, mana, turn, frozen cards, etc.)
- Factory-based card and hero creation
- Automatic game reset upon hero defeat

---

## Architecture Overview

The project is structured around several core layers:

### Card Hierarchy

- **Card**  
  Base class representing a generic card.

- **Hero (abstract)**  
  Represents a hero card. Each hero implements its own special ability via
  an overridden `useAbility` method.

- **Minion (abstract)**  
  Represents a minion card. Special minions override `executeAbility`
  to define unique behaviors.

- **Normal Minions**  
  Standard minions without special abilities.

- **Special Ability Minions**  
  Minions implementing custom gameplay mechanics.

---

### Game Core

- **Board**  
  Represents the game board and card placement logic.

- **Deck**  
  Manages card decks for each player.

- **Player**  
  Encapsulates player state, including deck, hand, mana, hero, and turn information.

- **Game**  
  Central game engine responsible for initializing the game state, processing actions,
  executing commands, and handling game progression.

---

## Command System

The game uses the **Command design pattern** to encapsulate all possible player actions.
Each command represents a single game operation and can be executed independently.

Examples of supported commands:
- Placing cards on the board
- Attacking minions or heroes
- Using hero or card abilities
- Querying game state (mana, turn, cards on board/hand)
- Retrieving frozen cards or player statistics

Commands are stored in a `HashMap` for constant-time lookup, allowing new commands
to be added without modifying existing logic.

---

## Design Patterns Used

- **Command Pattern**  
  Used to encapsulate game actions and decouple action execution from game logic.

- **Factory Pattern**  
  Used for creating cards and heroes, simplifying object creation and improving
  extensibility.

- **Inheritance & Polymorphism**  
  Applied extensively in the card hierarchy to support different behaviors for heroes
  and minions.

---

## Design Decisions

- A `HashMap` is used to map action names to command implementations, ensuring O(1)
  command resolution and easy extensibility.
- Game state is centralized in the `Game` class to maintain consistency and control
  flow.
- Card abilities are implemented via method overriding to preserve clean polymorphic
  behavior.
- The system is designed to closely mirror real card-game mechanics while remaining
  modular and testable.

---

## How to Run

1. Compile the project using a Java-compatible build system.
2. Run the main entry point provided by the assignment framework.
3. The game engine reads predefined actions, processes them sequentially, and outputs
   the resulting game state.

---

## Conclusion

This project strengthened my understanding of object-oriented design principles,
design patterns, and complex state management in interactive systems. It also provided
hands-on experience building a modular game engine capable of handling extensible
actions and dynamic gameplay logic.
