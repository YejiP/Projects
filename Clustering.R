library(dplyr)
library(ggplot2)

set.seed(2018)

#make random data to cluster
synth.data <- data.frame(x1 = c(rnorm(20, 3, 1.5), rnorm(20,0,1), rnorm(20,5,1)), 
                         x2 = c(rnorm(20, 0, 1), rnorm(20,4,1), rnorm(20,5,1)))

ndata <- nrow(synth.data) 
ndim <- ncol(synth.data)

#plot the data
synth.data %>% ggplot(aes(x = x1, y= x2)) + geom_point(shape = 1) + theme_bw()

#Euclidean distance
u_dist <- function(u, v){ 
  sqrt(sum((u - v) ** 2))
}

#we will make (k=3) k_means model.
k <- 3 
cents <- data.frame(cl = 1:k)
# pick three coordinate
cents <- cbind(cents, synth.data[sample(1:60, k),])
cents

#make it as a function, and iterate until it does not change.
# cent_f <- function(cents){
#   #a: distance between cent1 and x,b: distance betweencent 2 and x, c: distance between cent3 and x.
#   a <- apply(synth.data, 1, function(x){u_dist(cents[1,2:3],x)})
#   b <- apply(synth.data, 1, function(x){u_dist(cents[2,2:3],x)})
#   c <- apply(synth.data, 1, function(x){u_dist(cents[3,2:3],x)})
#   
#   #each row contains the information of distance between data and centroid.
#   mat <- data.frame(dcent1 = a, dcent2 =b , dcent3 = c)
# 
#   mat_min <- apply(mat,1,which.min)
# 
#   #make a group using an index.
#   gr1 <- synth.data [mat_min == 1,]
#   gr2 <- synth.data [mat_min == 2,]
#   gr3 <- synth.data [mat_min == 3,]

# #  calculate mean in the gr 1,2,3 . and save it in the variable n_cents
# n_cents <- data.frame(cl = 1:3 , x1 = c(mean(gr1[,1]),mean(gr2[,1]),mean(gr3[,1])),x2 = c(mean(gr1[,2]),mean(gr2[,2]),mean(gr3[,2])))
# #compare all the elements in the matrix
# all(n_cents == cents)
# 
#if centroid does not change, then stop.
#   if ( all(n_cents == cents) ){ 
#     synth.data$cl <- mat_min
#     synth.data %>% 
#       ggplot(aes(x = x1, y= x2, col = cl)) + geom_point(shape = 1) + theme_bw() + geom_point(data = n_cents, shape = 4, col = 'red')
#   }
#   else{
#     #if it changes, then do it again wiht new centroid. (recursive function.)
#     cent_f(n_cents)
#   }
# }
# 
# 
# mat_min
# 
# cent_f(cents)
# mat_min
#this works at the R studio, but not when i kint this as html. so, just make another version with the same logic.


#to make mat min , we already know 4 times is optimal (from the previous one).
for (i in 1:4){
  a <- apply(synth.data, 1, function(x){u_dist(cents[1,2:3],x)})
  b <- apply(synth.data, 1, function(x){u_dist(cents[2,2:3],x)})
  c <- apply(synth.data, 1, function(x){u_dist(cents[3,2:3],x)})
  
  mat <- data.frame(dcent1 = a, dcent2 =b , dcent3 = c)
  mat_min <- apply(mat,1,which.min)
  
  gr1 <- synth.data [mat_min == 1,]
  gr2 <- synth.data [mat_min == 2,]
  gr3 <- synth.data [mat_min == 3,]
  cents <- data.frame(cl = 1:3 , x1 = c(mean(gr1[,1]),mean(gr2[,1]),mean(gr3[,1])),x2 = c(mean(gr1[,2]),mean(gr2[,2]),mean(gr3[,2])))
}
mat_min
synth.data$cl <- mat_min
synth.data %>% 
  ggplot(aes(x = x1, y= x2, col = cl)) + geom_point(shape = 1) + theme_bw() + geom_point(data = cents, shape = 4, col = 'red' )





library(recommenderlab) 
  library(dplyr) 
  library(tidyr)
  library(tibble) 
  library(doBy)
  load('ratings.RData')
  #Pearson correlationfunction.
  pearsonCor <- function(x, y){ 
    x_mean <- mean(x, na.rm = T) 
    y_mean <- mean(y, na.rm = T) 
    idx <- !is.na(x) & !is.na(y) 
    if(sum(idx) == 0) return(NA) 
    x_new <- x[idx] 
    y_new <- y[idx] 
    sum((x_new- x_mean) * (y_new -y_mean)) / sqrt( sum( (x_new - x_mean)**2) * sum( (y_new-y_mean) **2) )
  }
  
  
  recommend <- function(u){
    sim <- apply(ratings, 1, function(x) {
      pearsonCor(u, x)
    })
    sim
    k = 2
    
    k_neighbors <- setdiff(which.maxn(sim, k+1), 5) 
    k_recommend <- apply(ratings[k_neighbors,], 2, function(x) { mean(x, na.rm = T)})
    k_recommend
    
    k_recommend_final <- k_recommend[is.na(u)]
    print(sort(k_recommend_final, decreasing = T))
  }
  
  #Apply it to all users
  apply(ratings,1, function(x){recommend(x)})
  
  
  ratings.df <- read.csv('train_v2.csv')
  #delete user id, and move user to rowname.
  rating_matrix <- ratings.df %>% 
    select(-ID) %>% 
    spread(movie, rating) %>% 
    remove_rownames() %>% 
    column_to_rownames(var = 'user')
  
  #make it as continuous data type
  rating_rrm <- as(as(rating_matrix, 'matrix'), 'realRatingMatrix') 
  # row name : movie name, col name : users
  # I picked the movies which have more than 51 reviews,and users which rate over 100 reviews.
  rating_rrm <- rating_rrm[rowCounts(rating_rrm) > 50, colCounts(rating_rrm) > 100]
  
  
  #normalize the data(becuase there would be some poeple who would like to give it only biased score, 4,5 . or 1,2)
  rating_rrm_norm <- normalize(rating_rrm)
  image(rating_rrm[1:50, 1:50]) 
  image(rating_rrm_norm[1:50, 1:50])
  
  print(object.size(rating_matrix), units = "auto")
  print(object.size(rating_rrm), units = "auto")
  
  
  recommenderRegistry$get_entries(dataType = "realRatingMatrix")
  #User based CF
  ubcf_model <- Recommender(rating_rrm, method = 'UBCF')
  #모델과 실제를 본다. 
  recom <- predict(ubcf_model, rating_rrm[1:2, ]) 
  recom <- predict(ubcf_model, rating_rrm[1:2, ], type = 'ratings') 
  recom
  
  
  # divide into two sets, train and test
  e <- evaluationScheme(rating_rrm, method="split", train=0.8, given=10) 
  #r1: user based. r2: item based.
  r1 <- Recommender(getData(e, "train"), "UBCF") 
  r2 <- Recommender(getData(e, "train"), "IBCF")
  #which one is more accurate?
  p1 <- predict(r1, getData(e, "known"), type="ratings") 
  p2 <- predict(r2, getData(e, "known"), type="ratings")
  
  error <- rbind( calcPredictionAccuracy(p1, getData(e, "unknown")), 
                  calcPredictionAccuracy(p2, getData(e, "unknown"))
  )
  
  rownames(error) <- c("UBCF","IBCF")
  error
