# Some interview exercise...

The main classes are:
* Customer - it contains the bare minimum (an id and a cart)
* Product - it describes an item by its name, price and "countability" (whether the product is countable or weighable)
* Cart - holds a set of products and their respective quantities added so far, with methods to add / remove products
* DiscountAB and extensions - they represent the various types of discounts
* Purchase - it's used to hold a Product and allows to apply discounts to it; that logic doesn't have to be known by a Cart,
hence it resides in its own class.
* CartPriceCalculator - it does what it says! Figures out full prices / discounted prices / savings of a cart


Assumptions:
* Each Product can only be affected by 1 discount at any given time.
Reason: I've never seen a supermarket item subject to 2+ discounts
* No "services" are sold, hence all products have a quantity.
Reason: services are generally not sold in supermarkets but only some type of businesses

Design choices
* I tried to respect the Single Responsibility principle by creating one class per concept
e.g. the Cart doesn't need to know about discounts
* All discounts have some common code, hence the creation of DiscountAB(stract)
* I've modelled the Kg @£ discount as an extension of a "N for Money" discount (e.g. 3x chocolate @£1.50),
since that could be interpreted by replacing N with the constant 1000 (as in grams per KG).
Hence I created KgAtMoneyDiscount as an extension of NforMoneyDiscount.

Testing
* I've followed a "partial TDD" approach; some methods were created before tests, some after.
Overall I achieved a significant coverage, I hit >90% LOC coverage on the core classes.
