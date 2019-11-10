BASEDIR=$(dirname "$0")
for (( i = 1; i <= 9; i++ )); do
  skipTest=$(git update-index --skip-worktree "${BASEDIR}/T0${i}/out.txt")
  $skipTest
  echo "Skipped T0${i} out."
done
for (( i = 10; i <= 17; i++ )); do
  skipTest=$(git update-index --skip-worktree "${BASEDIR}/T${i}/out.txt")
  $skipTest
  echo "Skipped T${i} out."
done
git update-index --skip-worktree "${BASEDIR}/out.txt"
echo "Skipped Manual out."
read -p "Press any key to continue..." -n1 -s
echo
