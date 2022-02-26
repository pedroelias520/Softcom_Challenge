package com.example.softcom_challenge.ViewModels
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.R
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager

class ExposedDB {
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

    init {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            try{
                SchemaUtils.createDatabase()
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
        }
    }
}



