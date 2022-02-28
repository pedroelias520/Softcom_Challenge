package com.example.softcom_challenge.ViewModels
import com.example.softcom_challenge.Models.*
import com.example.softcom_challenge.R
import com.example.softcom_challenge.ViewModels.ExposedDB.Request.autoIncrement
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager
import java.util.Random

class ExposedDB {
    object Sectors: Table(){
        val id:Column<Int> = integer("id").autoIncrement()
        val title: Column<String> = varchar("title",256)
        val image:Column<Int> = integer("image")
    }
    object Product: IntIdTable(){
        val sequelId:Column<Int> = integer("sequelId")
        val name:Column<String> = varchar("name",256)
        val category :Column<String> = varchar("category",256)
        val price:Column<Double> = double("price")
        val image:Column<Int> = integer("image")
        val oldPrice:Column<Double> = double("oldPrice")
        val description:Column<String> = varchar("description",300)
    }
    object Request:Table(){
        val id:Column<Int> = integer("id").autoIncrement()
        val productIds:Column<Int> = integer("productIds").references(Product.sequelId)
        val totalPrice:Column<Double> = double("totalPrice")
        val observation:Column<String>  = varchar("observation", 300)
        val qtd:Column<Int> = integer("qtd")
        val date:Column<String> = varchar("date",50)
    }
    fun loadSectorsRecycler(): ArrayList<com.example.softcom_challenge.Models.Sectors> {
        transaction {
            val sectorsList = Sectors.selectAll()
            sectorsList.forEach {
                Sectors_List.add(Sectors(title = it[Sectors.title], image = it[Sectors.image]))
            }
        }
        return Sectors_List
    }
    fun loadProductsRecycler(categoryFilter:String) {
        try {
            transaction {
                Product.select { Product.name eq categoryFilter }.forEach {
                
                }
            }
        }catch (e:Exception){
            println("Not found 404")
        }
    }
    fun getRandomCategory():String{
        var title:String = ""
        transaction {
                val number = Random().nextInt(5)
                Sectors.select { Sectors.id eq number }.forEach {
                    title =  it[Sectors.title]
                }
        }
        return title
    }

    init {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            try{
                SchemaUtils.createDatabase()
                SchemaUtils.create(Sectors)
                SchemaUtils.create(Product)
                SchemaUtils.create(Request)
                getRandomCategory()
            }catch (e:Exception){
                print("============================================ \n")
                print("Oh no! a error founded: ${e} \n")
                print("============================================ \n")
            }
        }
        transaction {
            try {
                for (i:Int in 1..10){
                    Product.insert {
                        it[sequelId] = i
                        it[name] = "Ração bom dog n°${i}"
                        it[category] = getRandomCategory()
                        it[price] = 39.90
                        it[image] = R.drawable.toy_icon
                        it[oldPrice] = 35.50
                        it[description] = "Uma bela raça pro seu cachorro"
                    }
                }
                Request.insert {
                    it[Request.id] = 1
                    it[Request.productIds] = 1
                    it[Request.totalPrice] = 107.20
                    it[Request.observation] = "Brinde"
                    it[Request.qtd] = 2
                    it[Request.date] = "26/02/2022"
                }
                Sectors.insert {
                    it[Sectors.title] = "Comedouros"
                    it[Sectors.image] = R.drawable.utensilios
                }
                Sectors.insert {
                    it[Sectors.title] = "Camas"
                    it[Sectors.image] = R.drawable.cama
                }
                Sectors.insert {
                    it[Sectors.title] = "Casinhas"
                    it[Sectors.image] = R.drawable.lar
                }
                Sectors.insert {
                    it[Sectors.title] = "Brinquedos"
                    it[Sectors.image] = R.drawable.basquetebol
                }
                Sectors.insert {
                    it[Sectors.title] = "Remédios"
                    it[Sectors.image] = R.drawable.medicina
                }


            }catch (e:Exception){
                println("ERROR TO INSERT OU READ \n ${e}")
            }
        }
    }
}



