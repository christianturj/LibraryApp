# LibraryApp
Sample library application built in Java! Uses State design pattern and features GUI and data serialization.

Each screen implements AppState, and MainFrame swaps screens with changeState(). At launch, MainFrame loads books and customers from books.txt and customers.txt through DataManager using Java serialization, shows LoginState, and saves on exit.
Sign in as the owner with admin/admin to reach OwnerStartState. There you manage books and customers; any change updates the in‑memory lists and will be saved when the app closes.

Customers sign in to reach CustomerStartState. They pick books and buy. Checkout adds up prices, lets them redeem points (redeem equals points divided by 100, total never goes below zero), updates the points balance, removes the purchased books, and then shows CustomerCostState with the final total.

To run, compile the sources and execute main. If you want sample data before first use, run DataGenerator once!

