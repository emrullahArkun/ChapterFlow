import { usePinstripeBackground } from '../shared/hooks/usePinstripeBackground';
import BookSearch from '../features/book-search/BookSearch';
import styles from './HomePage.module.css';

const HomePage = () => {
    usePinstripeBackground();

    return (
        <div className={styles.homeContent}>
            <BookSearch />
        </div>
    );
};

export default HomePage;
