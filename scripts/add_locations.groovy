
@GrabConfig(systemClassLoader=true)
import groovy.sql.Sql

@Grapes(
        @Grab(group='postgresql', module='postgresql', version='9.1-901.jdbc4')
)

def dbUrl      = "jdbc:postgresql://localhost:5432/stickleback_dev"
def dbUser     = "ubuntu"
def dbPassword = ""
def dbDriver   = "org.postgresql.Driver"
 
def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)


def answer = 0
sql.eachRow("select count(*) from stock ") { row ->
    answer = row[0]
}
assert answer > 0
println "# of stocks ${answer}"


File f = new File("../doc/exports/active_export_file/individual-locations.csv")
println "path: ${f.absolutePath}"
assert f.exists()

// column 1 is stock ID,
// column 2 is the fin-clip location,
// column 3 is the soma location
int i = 0
f.eachLine { line ->
//    if(i < 5){
//        println "line: ${line}"
        def columns = line.split(",")
//        println "${columns} and size ${columns.size()}"

        String stockId = columns[0]
//        println "A: ${stockId}"
        stockId = stockId.replaceAll("\\*","")
//        println "B: ${stockId}"

        Integer stock_id = stockId.split("\\.")[0] as Integer
        Integer individual_id= stockId.split("\\.")[2] as Integer

        String finClipLocation = columns[1]
        String somaLocation = columns[2]

//        println "stock ${stock_id} vs ${individual_id}"
//        println "fin: ${finClipLocation}"
//        println "soma: ${somaLocation}"
        def sqlString =  "update individual "
        sqlString += " set finclip_location = '${finClipLocation.trim()}' "
        sqlString += " , soma_location = '${somaLocation.trim()}' "
        sqlString += " where exists ( "
        sqlString += "   select * from stock s "
        sqlString += "   where s.stockid = ${stock_id} "
        sqlString += "   and stock_id=s.id "
        sqlString += "   and individualid = ${individual_id} "
        sqlString += " )  "
        sql.executeUpdate(sqlString)
//    }
    ++i
}

println "${i} rows updated "


