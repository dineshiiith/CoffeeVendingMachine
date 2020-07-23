The Idea is that there is a VendingMachine which has a list of recipies and Bags. Bags store the ingridient's amount.
during creation of an instance you need to specify the servingSize which can't be changed after creation

A User is basically a thread that tries to order a beverages concurrently.

A vending machine can serve n people simultaneously when extra people come it says them to wait until one of the serving
counter is free.

So when a person gets a counter, it first checks whether all ingridients it require are not locked if locked it waits
if not locked then it checks whether there are enough ingridients to make the beverage
if enough ingridients are not present then it that tells you that it cant make the order.
if the ingridients are present then it fetches(basically subtracts the ingridient)  ALL ingridients in FETCHING_FROM_BAG_TIME(defined in constants class).
During fetching an ingrdient it locks that ingridient.  it means no other user who needs that paticular ingridient can't access it for
FETCHING_FROM_BAG_TIME
After FETCHING_FROM_BAG_TIME it unlocks the ingridient and now the ingrdients came to the counter and it is time to prepare the
beverage for ORDER_PREPARATION_TIME. This is done in parallel since each counter can simultaneously make a beverage.
And once a Food is made it comes out of the counter and another person who is waiting gets the counter


Cases: if order with different ingridients come them they DONT need to wait FETCHING_FROM_BAG_TIME each. they can be done
simultaneously. When orders of same ingridient come they have to WAIT  until other user releases them.
When an order fetches its ingridients, it runs in parallel for their beverage making time i.e ORDER_PREPARATION_TIME

In short my implementation is:

    For queue implementation i have a currentSize variable in VendingMachine which has the count of people at counters
when a person comes it checks whether currentSize is equal to servingSize. if true waits there.
Once an order is completed currentSize decreases by one therefore allowing another person waiting in queue to order

For ingridient locks i had a Boolean (isLocked) in everyBag. when an order comes then it checks whether
all ingridients are not locked if locked waits them to unlock
once all ingridients are unlocked then it applies its locks and fetches(i.e. subtracts ingridients) ALL ingridients in
FETCHING_FROM_BAG_TIME After fetching it releases the locks.

All critical Section of code is handled.


TestingGround is where i wrote my testCases

PS: my test cases use the data.json file and my explanations in each testCase are based on that data file.

External Libraries: org.json:json:20171018 for reading json file
used gradle as depenendency manager