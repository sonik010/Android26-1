package model

enum class Position(val russian: String) {
    MIDFIELD("Полузащитник"),
    DEFENDER("Защитник"),
    FORWARD("Нападающий"),
    GOALKEEPER("Вратарь")
}

class Player (
    val name: String,
    val team: Team,
    val position: Position,
    val nationality: String,
    val agency: String?,
    val transferCost: Int,
    val participations: Int,
    val goals: Int,
    val assists: Int,
    val yellowCards: Int,
    val redCards: Int,
)
