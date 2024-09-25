package CommandOfTheSeas.model

class Destroyer(xPos: Int, yPos: Int, team: Int) extends GridNode(
  health = 50,
  range = 2,
  damage = 30,
  speed = 3,
  isPiece = true,
  xPos = xPos,
  yPos = yPos,
  team = team,
  imagePath = if (team == 1) "/CommandOfTheSeas/images/ShipDestroyer1.png" else "/CommandOfTheSeas/images/ShipDestroyer2.png",
  name = "Destroyer"
)