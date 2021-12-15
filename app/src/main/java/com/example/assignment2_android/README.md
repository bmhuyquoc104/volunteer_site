# volunteer_site

1. Project's title: "Covid Volunteer Site"


2. How to install and Run the project
- Download the zip, connect to fireStore database and run it !

3. Features included
-Super User: able to login, see all sites (all info of the sites), update the sites and download (save to 
   external Storage)
-Leader : able to login, see leader sites info, update the sites and see + download list of users in the sites (
   save to external storage), when clicking on their side in google map, only allow to register for their friend but not
   for themselves. Only user more than 18 can create new site
-User : able to login , register new account, see brief detail of sites before joining. After registering -> able 
   to see sites info but not including user list (only leader and super user can see this). Can register for friend and check the profile.
   Can check sites that they attended to check whether they are "leader" or "volunteer". User can attend many sites and can also own many sites.
- Login verification: only allow correct email and password 
- Check and filter sites by their attributes
- Calculate distance from current location to any markers or addresses in the map.
- Show route between current location and destination by enable google map.
- Data always updated during the process
- User guide to guide new user
- Nice UI with proper theme

4.Features not finished on time 
- Notifications to all the users when data is changed.
-Draw manual route between 2 locations (this project use google maps to find route between 2 locations instead)
  
5. Bug in project
-Completely no bugs if run in the good laptop, computer and high internet condition (works perfectly in Mac in RMIT Maclab).
- If the internet is low or computer or too weak, may cause problem when getting data from firestore (need to add more time to delay for new Handler function)
 
6. Note
-Admin account (username: voquochuy, password:admin104), can be found in the project
-User account (can simply register in the apps)
   account for testing purposes:
   username:bmhuyquoc104, password:1234 
   