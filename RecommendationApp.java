import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import java.util.*;

public class RecommendationApp {

    public static void main(String[] args) {
        try {
           
            Map<Long, PreferenceArray> preferences = new HashMap<>();

            preferences.put(1L, new GenericUserPreferenceArray(Arrays.asList(
                    new GenericPreference(1L, 101L, 5.0f),
                    new GenericPreference(1L, 102L, 3.0f),
                    new GenericPreference(1L, 103L, 2.5f)
            )));
            preferences.put(2L, new GenericUserPreferenceArray(Arrays.asList(
                    new GenericPreference(2L, 101L, 2.0f),
                    new GenericPreference(2L, 102L, 2.5f),
                    new GenericPreference(2L, 103L, 5.0f)
            )));
            preferences.put(3L, new GenericUserPreferenceArray(Arrays.asList(
                    new GenericPreference(3L, 101L, 5.0f),
                    new GenericPreference(3L, 104L, 4.0f)
            )));
            preferences.put(4L, new GenericUserPreferenceArray(Arrays.asList(
                    new GenericPreference(4L, 105L, 4.5f),
                    new GenericPreference(4L, 103L, 4.0f)
            )));

            DataModel model = new GenericDataModel(preferences);

           
            UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // === Get recommendations for a user ===
            long userId = 1L;
            List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

            System.out.println("\n=== Product Recommendations for User ID: " + userId + " ===");
            for (RecommendedItem item : recommendations) {
                System.out.printf("Item ID: %d | Predicted Score: %.2f%n", item.getItemID(), item.getValue());
            }

        } catch (Exception e) {
            System.err.println("Error building recommendation system: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

