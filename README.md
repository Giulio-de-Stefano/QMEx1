# QMetricShopThingy

The main classes are:
* Customer - it contains the bare minimum (an id and a cart)
* Product - it describes an item by its name, price and "countability" (whether the product is countable or weighable)
* Cart - holds a set of products and their respective quantities added so far, with methods to add / remove products
* DiscountAB & extensions - they represent the various types of discounts
* Purchase - it's used to hold a Product and allows to apply discounts to it; that logic doesn't have to be known by a Cart,
hence I kept it resides in its own class.



Assumptions:
* each Product can only be affected by 1 discount at any given time.
Reason: I've never seen a supermarket item subject to 2+ discounts

* no "services" are sold, hence all products have a quantity
Reason: services are generally not sold in supermarkets but only some type of businesses

Design choices
* I tried to respect the Single Responsibility principle by creating one class per concept
e.g. the Cart doesn't need to know about discounts

* All discounts have some common code, hence the creation of DiscountAB(stract)

* I've modelled the Kg @£ discount as an extension of a "N for Money" discount (e.g. 3x chocolate @£1.50),
since that could be interpreted by replacing N with the constant 1000 (as in grams per KG).
Hence I created KgAtMoneyDiscount as an extension of NforMoneyDiscount.

* I've followed a "partial TDD" approach; some methods were created before tests, some after.
Overall I achieved a significant coverage, trying to hit 80%+ LOC.

About the bonus task:
I have no experience in designing REST APIs, but here I give it a try:
TODO