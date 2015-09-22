# PercolateTest
Developed an Android Application based on the requirements provided in the link http://percolate.github.io/coffeeapi/.

Notes
1. Application is built using Android Studio
2. Network calls are made through Retrofit Library
3. Used Picasso(square) for loading images.
4. Used Jackson for JSON Parsing
5. minSDKversion is 14.
6. Two Activities and two fragments (Main, Detail) are used to accomplish the task.
7. Coffee item name,desc,imagurl are updated in Mainfragment api call and
Last Updated Time is updated in Detail Fragment (api/coffee/{id}) using CoffeeHelper database helper class.
8 Themed Action bar as per the provided colors and icons.
9. Splash screen Added

Assumptions

1. This task can be achieved with and without using database and directly loading items in ListView.
Since its effective & best practise I used databases (also added content provider if the data needs to be shared across)
to store the items and Cursor loader to load items to Cursor Adapter.
2. Some of the items contents are empty (only have id) in api/coffee/ call. I am loading all items whether they are empty or not
Since the problem statement doesnt define whether to show empty items or not.
3. Used Fragments and Activities to make the application modular and fragments can be reused.
4. Last updated was in weeks in storyboard. so calculated no of weeks from last updated time and now and displayed time in weeks. if less than a week
last updated time would be the time obtained from the call.
5. There is no clear definition of what share should do in requirements. I assumed to share name, desc and image link. Also used
Action Layout to achieve share button
6. Layouts spacings and font sizes dimens are decided approximately to match story board look
since there are no definitive specs for the storyboard.
