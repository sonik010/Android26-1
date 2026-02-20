package resolver

import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import model.Player
import model.Position
import model.Team


class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int = players.count{it.agency == null}

    override fun getBestScorerDefender(): Pair<String, Int> {
        val best = players // ищем лучшего защитника (возможен null)
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
        return best?.let { it.name to it.goals } // найден -> пара (имя, гол)
            ?: ("" to 0) // иначе -> пара ('', 0)
    }


    override fun getTheExpensiveGermanPlayerPosition(): String {
        val goldplater = players
            .filter { it.nationality == "Germany" } // только немцы
            .maxByOrNull { it.transferCost }    // самый дорогой
            ?.position  // получаем позицию (enum)
            ?.toString()    // enum -> строку
            ?: "Unknown"    // если нет немцев

        val positionRus = mapOf(    // словарь для перевода
            "MIDFIELD" to "Полузащитник",
            "DEFENDER" to "Защитник",
            "FORWARD" to "Нападающий",
            "GOALKEEPER" to "Вратарь",
        )

        return positionRus[goldplater] ?: "Неизвестно"  // возвращаем перевод если есть
    }


    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }    // группируем по командам
            .map { (team, teamPlayers) ->   // для каждой команды счит. среднее кол-во redcards
                val avgredcards = teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
                team to avgredcards // пара (команда, сред. знач.)
            }
            .maxByOrNull { it.second }  // пара с max сред. кол-во redcards
            ?.first // берем название команды
            ?: Team("","")  // если нет -> пустая команда
    }

    fun getTheShareOfPlayersByCountry(): Map<String, Double> {
        if (players.isEmpty()) return emptyMap()
        val totalPlayers = players.count().toDouble()

        val result = players
            .groupBy { it.nationality.trim() }
            .map { (country, playersInCountry) ->
                val percentage = playersInCountry.size.toDouble() / totalPlayers * 100
                val shortCountry = if (country.length > 10) country.take(10) + "..." else country
                shortCountry to percentage
            }
            .toMap()


        val df = dataFrameOf(
            "Страна" to result.keys.toList(),
            "Процент" to result.values.toList()
        )

        df.plot {
            bars {
                x("Страна")
                y("Процент")
            }
        }.save("variant4.png")

        return result
    }

}