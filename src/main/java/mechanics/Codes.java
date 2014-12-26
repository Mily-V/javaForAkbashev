package mechanics;

/**
 * Created by Vadim on 08.11.14.
 */
public enum Codes {
    OK,
    ERROR,                  // При ошибке размещения кораблей
    EMPTY,                  // Мимо
    DECK,                   // Попал
    KILLED,
    IS_FIRED,               // Возможно, если в ячейку уже стреляли
    CELL_DOES_NOT_EXIST,    // Возможно при выходе за пределы поля
    USER_PLAYS,
    USER_NOT_FOUND,
    FIELD_EXIST,
    FIELD_IS_EMPTY,          // Если field == null
    GAME_OVER                // Если убит последний корабль противника
}