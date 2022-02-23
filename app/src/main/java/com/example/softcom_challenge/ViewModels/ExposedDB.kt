package com.example.softcom_challenge.ViewModels
import com.example.softcom_challenge.Models.Product
import com.example.softcom_challenge.R
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class ExposedDB {
    object Category: Table(){
        val id = integer("id").uniqueIndex()
        val title =  varchar("title",20)
        val productsIds = integer("productIds").references(Product.id).nullable()
    }
    object Product: Table(){
        val id = integer("id").uniqueIndex()
        val name = varchar("name",256)
        val price = double("price")
        val image = integer("image")
        val oldPrice = double("oldPrice")
        val description = varchar("description",300)
        override val primaryKey = PrimaryKey(id, name = "PK_Product_ID")
    }
    object Request:Table(){
        val id = integer("id").autoIncrement()
        val productIds = integer("productIds").references(Product.id)
        val totalPrice = double("totalPrice")
        val observation  = varchar("observation", 300)
        val qtd = integer("qtd")
        val date = varchar("date",50)
    }

    init {
        val db = Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            SchemaUtils.create(Product)
            SchemaUtils.create(Category)
            SchemaUtils.create(Request)
            Product.insert {
                it[id] = 1
                it[name] = "Ração bom dog"
                it[price] = 39.90
                it[image] = R.drawable.toy_icon
                it[oldPrice] = 35.50
                it[description] = "Uma bela raça pro seu cachorro"
            }
        }
    }
}



