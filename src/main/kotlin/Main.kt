import com.github.javafaker.File
import com.sun.org.apache.bcel.internal.classfile.Field
import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    val players = java.io.File("src/main/resources/fakePlayers.csv").readLines()
    val result = CsvParser.parsePlayers(players)

    val resolver = Resolver(result)
    val (name, goals) = resolver.getBestScorerDefender()

    println("Количество игроков, интересы которых не представляет агенство: ${(resolver.getCountWithoutAgency())}" )
    println("Автор наибольшего числа голов из числа защитников: $name: $goals(число голов)")
    println("Русское название позиции самого дорогого немецкого игрока: ${(resolver.getTheExpensiveGermanPlayerPosition())}")
    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${(resolver.getTheRudestTeam().name)}")
    println("вариант 4: ${(resolver.getTheShareOfPlayersByCountry())}")
}

