package parser

import model.Team
import model.Player
import model.Position
import kotlin.String

object CsvParser {

    fun parsePlayers(csv: List<String>): List<Player> {
        return csv
            .drop(1) // пропускаем первую строку
            .filter { it.isNotBlank() } // удаляем пустые строки
            .map { parseLine(it) } // преобр. каждую строку в объект Player с помощью parseLine
    }

    private fun parseLine(line: String): Player {
        val row = line.split(';') // разделяем строку по ;

        val team = Team (
            name = row[1].trim(),
            city = row[2].trim()
        )

        return Player(
            name = row[0].trim(), // .trim() - удаляет пробелы в начале и в конце
            team = team,
            position = Position.valueOf(row[3].trim()),
            nationality = row[4].trim(),
            agency = row[5].trim().ifEmpty { null },
            transferCost = row[6].trim().toInt(),
            participations = row[7].trim().toInt(),
            goals = row[8].trim().toInt(),
            assists = row[9].trim().toInt(),
            yellowCards = row[10].trim().toInt(),
            redCards = row[11].trim().toInt()
        )
    }
}