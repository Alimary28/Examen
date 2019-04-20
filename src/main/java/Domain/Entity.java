package Domain;

import java.util.Objects;

public abstract class Entity {

        protected String id;

        public Entity(String id) { this.id = id; }

        @Override
        public String toString() {
            return "Entity{" +
                    "id='" + id + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Entity entity = (Entity) obj;
            return Objects.equals(id, entity.id);
        }

        @Override
        public int hashCode() { return Objects.hash(id); }

        public String getId() { return id; }

    }
