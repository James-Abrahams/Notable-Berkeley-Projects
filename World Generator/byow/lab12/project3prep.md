# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: I didn't have a full answer, I actually planned on putting a method into hexworld that creates a hexagon of a certain size
but the lab solution used mulitple classes and advanced math to give a more abstract answer.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: The Hexagon would be the individual rooms and hallways, and the tesselation is the entirety of the world.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would probably make a room class and a hallway class.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallways connect two rooms and have particular dimensions.  Rooms have doors, and only connect 
to hallways with doors.
