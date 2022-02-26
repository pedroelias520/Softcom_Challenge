package com.example.softcom_challenge.ViewModels
import com.example.softcom_challenge.Models.Comedouros
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.Models.Sectors
import com.example.softcom_challenge.Models.Sectors_List
import com.example.softcom_challenge.R
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager

class ExposedDB {
    object Sectors: Table(){
        val title: Column<String> = varchar("title",256)
        val image:Column<Int> = integer("image")
    }
    object Category: Table(){
        val id:Column<Int> = integer("id").uniqueIndex()
        val title:Column<String> =  varchar("title",256)
        val productsIds:Column<Int?> = integer("productIds").references(Product.sequelId).nullable()
    }
    object Product: IntIdTable(){
        val sequelId:Column<Int> = integer("sequelId")
        val name:Column<String> = varchar("name",256)
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

    init {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            try{
                SchemaUtils.createDatabase()
                SchemaUtils.create(Sectors)
                SchemaUtils.create(Product)
                SchemaUtils.create(Category)
                SchemaUtils.create(Request)
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
                    it[Sectors.image] = R.drawable.food_icon
                }
                Sectors.insert {
                    it[Sectors.title] = "Camas"
                    it[Sectors.image] = R.drawable.bed_icon
                }
                Sectors.insert {
                    it[Sectors.title] = "Casinhas"
                    it[Sectors.image] = R.drawable.house_icon
                }
                Sectors.insert {
                    it[Sectors.title] = "Brinquedos"
                    it[Sectors.image] = R.drawable.toy_icon
                }
                Sectors.insert {
                    it[Sectors.title] = "Remédios"
                    it[Sectors.image] = R.drawable.ic_baseline_healing
                }


            }catch (e:Exception){
                println("ERROR TO INSERT OU READ \n ${e}")
            }
        }
        transaction {
            val query = Product.selectAll()
            query.forEach {
                println("================================")
                println("${it[Product.name]} \n")
                println("${it[Product.sequelId]} \n")
                println("================================")
            }
            val requestList = Request.selectAll()
            requestList.forEach {
                println("================================")
                println("${it[Request.date]} \n")
                println("${it[Request.qtd]} \n")
                println("${it[Request.totalPrice]} \n")
                println("${it[Request.observation]} \n")
                println("${it[Request.productIds]} \n")
                Product.select { Product.sequelId eq it[Request.productIds] }.forEach {
                    println("PRODUTO DENTRO DO PEDIDO: ${it[Product.name]}")
                }
                println("================================")
            }
            val sectorsList = Sectors.selectAll()
            sectorsList.forEach {
                println("================================")
                println("${it[Sectors.title]} \n")
                println("${it[Sectors.image]} \n")
                println("================================")
            }
        }
    }
}



