# training-data
### Висновки

1. **Порівняння пошуку в масиві та HashSet:**
   - Пошук значення в масиві **швидший після сортування** (1717 наносекунд) порівняно з пошуком в масиві **до сортування** (11871 наносекунд).
   - Пошук в **HashSet** (26586 наносекунд) займає більше часу, ніж в масиві після сортування. Це може бути зумовлено різною структурою даних, яку використовує HashSet.

2. **Вплив сортування на швидкість пошуку в масиві:**
   - **Сортування масиву значно покращує швидкість пошуку**. Без сортування час пошуку був вищим, ніж після сортування (11871 наносекунд до сортування проти 1717 наносекунд після).

3. **Порівняння кількості елементів:**
   - В **HashSet** містилося **114 елементів**, в той час як в масиві — **200 елементів**. Це підтверджує, що HashSet зберігає лише унікальні елементи.

4. **Час пошуку мінімального і максимального значення:**
   - **Час для пошуку мінімального і максимального значення** в масиві після сортування був значно більший (101600 наносекунд), порівняно з без сортування (9228833 наносекунд). Це може бути наслідком додаткових операцій, які виконує алгоритм сортування.

### Загальний висновок:
- **Масиви після сортування значно швидші для пошуку**, ніж до сортування.
- **HashSet має більший час пошуку**, але перевага його в тому, що він зберігає тільки унікальні елементи.
- **Час сортування масиву** може впливати на загальну ефективність, але після сортування час пошуку значно покращується.
