package CommandOfTheSeas.model

class PatrolBoat(xPos: Int, yPos: Int, team: Int) extends GridNode(
  health = 25,
  range = 1,
  damage = 15,
  speed = 4,
  isPiece = true,
  xPos = xPos,
  yPos = yPos,
  team = team,
  imagePath = if (team == 1) "/CommandOfTheSeas/images/ShipPatrol1.png" else "/CommandOfTheSeas/images/ShipPatrol2.png",
  name = "Patrol Boat"
)